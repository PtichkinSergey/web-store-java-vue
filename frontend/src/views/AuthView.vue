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
                type="password"
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
      emailRules: [ 
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
      ]
    }),
    components: {
      Toolbar
    },
    methods: {
      login() {
        const baseURL = "http://localhost:5000/api/sign-in";
        let headers = {Authorization: ''};
          if(this.$store.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
        const user_email = this.email;
        axios.post(baseURL, {email: this.email, password: this.password}, {headers: headers})
          .then(response => {
            if(response.data.errorText) {
              alert(response.data.errorText);
              return;
            }
            localStorage.jwt = response.data.jwt
             const auth_data = {
              jwt: response.data.jwt,
              auth_email: user_email,
              auth_user_name: response.data.username
            }
            this.$store.commit('saveAuthData', auth_data)
            this.$router.push('/catalog');
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