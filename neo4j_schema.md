# Neo4j 数据表关系图（Mermaid）

![img.png](img.png)

## Description
- **SocialUser**: User node, contains basic user information.
- **Post**: Post node, contains content, image, time, etc.
- **HashTag**: Topic tag node.
- **FRIEND_OF**: Friendship relationship between users.
- **FOLLOWED_BY**: Follow relationship between users.
- **POSTED_BY**: Which user posted the post.
- **LIKE**: Which posts are liked by users.
- **TAGGED_WITH**: Which tags are included in the post.
- **INTERESTED_IN_HASHTAG**: Which tags users are interested in.

## Recommendation Algorithm Explanation

The backend provides friend and follow recommendations for users based on the social graph stored in Neo4j. The main logic is as follows:

- **Friend Recommendations**: For a given user, the system recommends other users who are not already friends, prioritizing those with more mutual friends. The query excludes users who are already friends and uses the number of shared friends (mutualFriends) as a key factor.
- **Follow Recommendations**: For a given user, the system recommends users they are not already following. The algorithm also considers mutual friends and existing relationships (such as whether the user is already a follower or has sent/received friend requests).
- **Implementation**: These recommendations are generated using Cypher queries in the repository layer (see `FriendsRelationshipRepository` and `FollowRelationshipRepository`). The queries leverage Neo4j's pattern matching to efficiently find suitable candidates and return paginated results as `SocialUserCard` objects, which include user info and relationship context (e.g., isFriend, isFollower, mutualFriends, etc).

This approach enables social recommendations similar to "People You May Know" or "Suggested Follows" in mainstream social platforms, leveraging the structure and relationships in the graph database.

## Example Cypher Queries for Recommendations (English)

### Friend Recommendations
```cypher
MATCH (au:SocialUser {identifier: $idUserRequest})
MATCH (uf:SocialUser)
WHERE au <> uf AND NOT (uf)-[:FRIEND_OF]-(au)

// Find relationship with authenticated user
OPTIONAL MATCH (au)<-[rfollow:FOLLOWED_BY]-(uf)
OPTIONAL MATCH (au)-[rfollower:FOLLOWED_BY]->(uf)
OPTIONAL MATCH (au)-[:FRIEND_OF]-(mf)-[:FRIEND_OF]-(uf)

// Determine if the users are...
WITH uf,
    COUNT(rfollow) > 0 AS isFollow,
    COUNT(rfollower) > 0 AS isFollower,
    COUNT(mf) AS mutualFriends
RETURN uf, isFollow, isFollower, mutualFriends
ORDER BY mutualFriends DESC
SKIP $skip
LIMIT $limit
```
**Explanation:**
- Recommends other users who are not already friends with the current user, prioritizing those with more mutual friends.
- Also returns relationship info such as whether already following or being followed.

### Follow Recommendations
```cypher
MATCH (au:SocialUser {identifier: $idUserRequest})
MATCH (uf:SocialUser)
WHERE au <> uf AND NOT (uf)-[:FOLLOWED_BY]->(au)

OPTIONAL MATCH (au)-[rfriend:FRIEND_OF]-(uf)
OPTIONAL MATCH (au)-[rfollower:FOLLOWED_BY]->(uf)
OPTIONAL MATCH (au)-[:FRIEND_OF]-(mf)-[:FRIEND_OF]-(uf)

WITH uf,
    COUNT(rfriend) > 0 AS isFriend,
    COUNT(rfollower) > 0 AS isFollower,
    COUNT(mf) AS mutualFriends
RETURN uf, isFriend, isFollower, mutualFriends
ORDER BY mutualFriends DESC
SKIP $skip
LIMIT $limit
```
**Explanation:**
- Recommends other users that the current user is not yet following, prioritizing those with more mutual friends.
- Also returns info such as whether already friends or following.

