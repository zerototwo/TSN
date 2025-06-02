<script setup>
import { ref, inject, onMounted } from "vue";
import axios from "axios";
const imgUrl = inject('imgUrl') || '';
const currentUser = ref(JSON.parse(localStorage.getItem('currentUser')));
const apiUrl = inject('apiUrl')
const socialUsers = ref([]);
const loading = ref(false);
const PAGE_SIZE = 4;
let currentPage = 0;

async function cargarDatos() {
  try {
    loading.value = true;
    const response = await axios.get(
        `${apiUrl}friend/friend-recommendations/`,
        {
          headers: {
            userId: currentUser.value.id,
          },
          params: {
            page: currentPage,
            size: PAGE_SIZE,
          },
        }
    );
    socialUsers.value = response.data.response.content;
    console.log('Friend Recommendations', response.data);
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  cargarDatos();
});

async function cargarMasSugerencias() {
  currentPage++;
  await cargarDatos();
}
</script>

<template>
  <div class="friend-recommend-list">
    <div class="header-row">
      <h2 class="title">People You May Want to Add as Friends</h2>
      <button @click="cargarMasSugerencias" :disabled="loading" class="refresh-btn" title="Refresh">
        <fa icon="fa-solid fa-sync" :class="{ 'animate-spin': loading }" />
      </button>
    </div>
    <div class="card-list">
      <div v-for="user in socialUsers" :key="user.id" class="user-card">
        <img :src="imgUrl + user.profileImage" alt="Avatar" class="avatar">
        <div class="info">
          <div class="name-row">
            <span class="fullname">{{ user.fullName }}</span>
            <span class="username">@{{ user.userName }}</span>
          </div>
          <div class="email">{{ user.email }}</div>
          <div class="stats">
            <span>Friends: {{ user.friendCount }}</span>
            <span>Following: {{ user.followingCount }}</span>
            <span>Followers: {{ user.followersCount }}</span>
          </div>
        </div>
        <button class="add-btn">Add Friend</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.friend-recommend-list {
  max-width: 400px;
  margin: 0 auto;
  padding: 16px 0;
}
.header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 18px;
}
.title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin: 0;
}
.refresh-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  color: #4f8cff;
  padding: 6px;
  border-radius: 50%;
  transition: background 0.2s;
}
.refresh-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.refresh-btn:hover:not(:disabled) {
  background: #f0f4fa;
}
.animate-spin {
  animation: spin 1s linear infinite;
}
@keyframes spin {
  100% { transform: rotate(360deg); }
}
.card-list {
  padding-right: 2px;
}
.user-card {
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
  padding: 14px 18px;
  margin-bottom: 18px;
  transition: box-shadow 0.2s, transform 0.2s;
}
.user-card:hover {
  box-shadow: 0 4px 16px rgba(0,0,0,0.13);
  transform: translateY(-2px) scale(1.02);
}
.avatar {
  width: 54px;
  height: 54px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 16px;
  border: 2px solid #e0e7ef;
}
.info {
  flex: 1;
  min-width: 0;
}
.name-row {
  display: flex;
  align-items: baseline;
  gap: 8px;
}
.fullname {
  font-weight: 600;
  font-size: 1.08rem;
  color: #222;
}
.username {
  font-size: 0.98rem;
  color: #888;
}
.email {
  font-size: 0.93rem;
  color: #aaa;
  margin: 2px 0 4px 0;
  word-break: break-all;
}
.stats {
  font-size: 0.92rem;
  color: #5a6;
  display: flex;
  gap: 12px;
}
.add-btn {
  background: linear-gradient(90deg, #4f8cff 0%, #6fd6ff 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 7px 18px;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s;
  margin-left: 16px;
  box-shadow: 0 1px 4px rgba(79,140,255,0.08);
}
.add-btn:hover {
  background: linear-gradient(90deg, #3576e6 0%, #4fd6ff 100%);
  box-shadow: 0 2px 8px rgba(79,140,255,0.13);
}
</style>
