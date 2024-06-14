<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card" id="auth_card">
            <h3>Вход в личный аккаунт</h3>
            <v-text-field
                :rules="emailRules"
                hide-details="auto"
                label="Email"
                v-model="email"
            />
            <v-text-field
                hide-details="auto"
                label="Password"
                v-model="password"
            />
            <v-btn
                class="accept_btn"
                @click="login"
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
  import axios from 'axios'
  export default {
    name: 'AuthView',
    data: () => ({
      email: '',
      password: '',
      username: '',
      emailRules: [ 
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
      ]
    }),
    components: {
      Toolbar
    },
    methods: {
      login() {
        axios.post('/api/login', {username: this.username, email: this.email, password: this.password})
          .then(response => {
            localStorage.token = response.data.token
            console.log("TOKEN: ", response.data.token)
          })
      }
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