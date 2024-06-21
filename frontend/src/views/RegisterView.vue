<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card" id="reg_card">
            <h3>Регистрация нового пользователя</h3>
            <v-text-field
                hide-details="auto"
                label="Имя"
                v-model="firstName"
            />
            <v-text-field
                hide-details="auto"
                label="Фамилия"
                v-model="secondName"
            />
            <v-text-field
                :rules="emailRules"
                hide-details="auto"
                label="Email"
                v-model="email"
            />
            <v-text-field
                hide-details="auto"
                type="password"
                label="Password"
                v-model="password"
            />
            <v-btn
                class="accept_btn"
                @click="register"
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
  import axios from 'axios'
  export default {
    name: 'RegisterView',
    data: () => ({
      firstName: '',
      secondName: '',
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
      register() {
        const baseURL = "http://localhost:5000/api/sign-up";
        let headers = {Authorization: ''};
          if(this.$store.state.jwt) {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
        const user_email = this.email;
        const user_name = this.firstName + ' ' + this.secondName;
        axios.post(baseURL, {firstName: this.firstName, secondName: this.secondName, email: user_email, password: this.password}, {headers: headers})
          .then(response => {
            if(response.data.errorText) {
              alert(response.data.errorText);
              return;
            }
            localStorage.jwt = response.data.jwt
            const auth_data = {
              jwt: response.data.jwt,
              auth_email: user_email,
              auth_user_name: user_name
            }
            this.$store.commit('setAuthData', auth_data)
            this.$router.go(-1);
          })
      }
    }
  }
</script>

<style lang="scss" scoped>
.page {
  text-align: center;
}
#reg_card {
  display: flex;
  flex-direction: column;
  width: 25vw;
}
.accept_btn {
  margin-top: 10px;
  background-color: rgb(177, 219, 128);
  font-weight: bold;
}
</style>