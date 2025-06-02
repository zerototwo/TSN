<script setup>
import {inject, ref} from "vue";
import FollowButton from "@/features/UserProfile/components/card/button/followButton.vue";
import unFollowButton from "@/features/UserProfile/components/card/button/unFollowButton.vue";
import sendRequestFriendshipButton from "@/features/UserProfile/components/card/button/sendRequestFriendshipButton.vue";
import cancelRequestFriendshipButton from "@/features/UserProfile/components/card/button/cancelRequestFriendshipButton.vue";
import cancelFriendshipButton from "@/features/UserProfile/components/card/button/cancelFriendshipButton.vue";
import RequestFriendshipReceived from "@/features/UserProfile/components/card/button/requestFriendshipReceived.vue";

// Props
const props = defineProps({
  user: Object,
  request: Boolean
})

// Transform user to Reactive
const socialUser = ref(props.user);

// url to load images
const imgUrl = inject('imgUrl');

function updateStatusFollow(status){
  socialUser.value.isFollow = status;
}

function updateStatusOfIsRequestFriendshipSending(status){
  socialUser.value.isRequestFriendshipSending = status;
}

function toggleStatusOfIsRequestFriendshipReceived(){
  socialUser.value.isRequestFriendshipReceived = !socialUser.value.isRequestFriendshipReceived;
}

function toggleStatusOfIsFriend(){
  socialUser.value.isFriend = !socialUser.value.isFriend;
}


</script>

<template>
  <div class="bg-gray-50 rounded-lg shadow-md mx-auto mt-4 p-4 max-w-lg">
    <div class="flex items-center justify-between">
      <div class="flex items-center">
        <img :src="imgUrl + user.profileImage" alt="User avatar" class="w-16 h-16 rounded-full mr-4">
        <div>
          <router-link :to="{ name: 'su-profile', params: { id: user.id } }">
            <h2 class="text-md font-semibold">{{ user.fullName }}</h2>
            <p class="text-sm text-gray-600">@{{ user.userName }}</p>
          </router-link>
          <span class="text-sm text-gray-600"><span class="font-semibold">{{ user.mutualFriends || 0 }}</span> mutual friends</span>
        </div>
      </div>
      <div>
        <button class="bg-blue-300 hover:bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg w-30 h-15 focus:outline-none focus:shadow-outline">
          <fa icon="fa-solid fa-user-plus" class="text-white-600"/>
          Add Friend
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>