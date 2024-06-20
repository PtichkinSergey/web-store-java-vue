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
          if(this.$store.state.jwt != '') {
            headers.Authorization = 'Bearer ' + this.state.jwt;
          }
        axios.post(baseURL, {firstName: this.firstName, secondName: this.secondName, email: this.email, password: this.password}, {headers: headers})
          .then(response => {
            localStorage.jwt = response.data.jwt
            this.$store.commit('saveJwt', response.data.jwt)
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