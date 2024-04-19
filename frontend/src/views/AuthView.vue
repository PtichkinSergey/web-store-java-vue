<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card" id="auth_card">
            <h3>Вход в личный аккаунт</h3>
            <v-text-field
                :rules="rules"
                hide-details="auto"
                label="Email"
            />
            <v-text-field
                :rules="rules"
                hide-details="auto"
                label="Password"
            />
            <v-btn
                class="accept_btn"
                @click="auth"
            >
                Войти
            </v-btn>
            <a 
                @click="this.$router.push('/register');"
                class="link"
            >
                Нет аккаунта? Создать
            </a>
        </v-card>
    </div>
</template>

<script>
  import Toolbar from '@/components/Toolbar.vue'
  export default {
    name: 'AuthView',
    components: {
      Toolbar
    }
  }
</script>

<style lang="scss" scoped>
.page {
  text-align: center;
}
#auth_card {
  display: flex;
  flex-direction: column;
  width: 15vw;
}
.accept_btn {
  margin-top: 10px;
  background-color: rgb(177, 219, 128);
}
</style>