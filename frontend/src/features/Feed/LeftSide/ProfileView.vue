<script setup>
import {inject, onMounted, ref} from "vue";
import axios from "axios";

const props = defineProps({
  user: String,
})

const currentUser = ref({});
const apiUrl = inject('apiUrl')
// url to load images
const imgUrl = inject('imgUrl');

async function userDataLoad(userId) {
  try {
    const response = await axios.get(
        `${apiUrl}socialUser/getSocialUserById/${userId}`
    );

    currentUser.value = response.data.response;
  } catch (error) {
    console.error(error);
  }
}


onMounted(() => {
  if (props.user === "me"){
    currentUser.value = JSON.parse(localStorage.getItem('currentUser'));
  } else {
    userDataLoad(props.user)
  }
});

</script>

<template>
  <div class="rounded-lg shadow w-30 bg-gray-50">
    <!--        Perfil-->
    <div class="bg-cover  h-20 rounded-t-lg" style="background-image: url('/img/background-user-test.jpg')"></div>
    <div class="p-4">
      <img :src="imgUrl + currentUser.profileImage" class="rounded-full w-16 h-16 border-white border-2 -mt-11 mx-auto" alt="">
      <h1 class="text-xl font-bold text-color-black text-center mt-2">{{ currentUser.fullName }}</h1>
      <p class="text-gray-700 text-center">{{ currentUser.bio }}</p>
      <!--      Follows  panel-->
      <div class="flex justify-center items-center space-x-4 mt-3">
        <span>
          <span class="text-black text-md font-bold">{{ currentUser.friendCount }}</span> Friends
        </span>
        <span>
          <span class="text-black text-md font-bold">{{ currentUser.followingCount }}</span> Following
        </span>
        <span>
          <span class="text-black text-md font-bold">{{ currentUser.followersCount }}</span> Followers
        </span>
      </div>

    </div>
  </div>
</template>

<style scoped>

</style>