<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card">
            <h3>Регистрация нового пользователя</h3>
            <v-text-field
                :rules="rules"
                hide-details="auto"
                label="Имя"
            />
            <v-text-field
                :rules="rules"
                hide-details="auto"
                label="Фамилия"
            />
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
              Зарегистрироваться
            </v-btn>
            <a 
                @click="this.$router.push('/auth');"
                class="link"
            >
                Уже есть аккаунт? Войти
            </a>
        </v-card>
    </div>
</template>

<script>
  import Toolbar from '@/components/Toolbar.vue'
  export default {
    name: 'RegisterView',
    components: {
      Toolbar
    }
  }
</script>

<style lang="scss" scoped>
.page {
  text-align: center;
}
.card {
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 25vw;
  border-radius: 15px;
  margin: 20px auto auto auto;
  padding: 20px;
}
.accept_btn {
  margin-top: 10px;
  background-color: rgb(177, 219, 128);
  font-weight: bold;
}
</style>