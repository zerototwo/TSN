# Neo4j 数据表关系图（Mermaid）



## 说明
- **SocialUser**: 用户节点，包含用户基本信息。
- **Post**: 帖子节点，包含内容、图片、时间等。
- **HashTag**: 话题标签节点。
- **FRIEND_OF**: 用户之间的好友关系。
- **FOLLOWED_BY**: 用户之间的关注关系。
- **POSTED_BY**: 帖子属于哪个用户。
- **LIKE**: 用户点赞了哪些帖子。
- **TAGGED_WITH**: 帖子包含哪些标签。
- **INTERESTED_IN_HASHTAG**: 用户对哪些标签感兴趣。

> 本图基于 `/fake_graph` 目录下的测试数据生成逻辑自动推断。