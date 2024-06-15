<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card" id="reg_card">
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
  export default {
    name: 'RegisterView',
    components: {
      Toolbar
    },
    methods: {
      register() {

        localStorage.setItem('todo_items', JSON.stringify(this.todo_items));
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