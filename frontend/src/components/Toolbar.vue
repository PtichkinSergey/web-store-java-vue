<template>
    <v-card>
        <v-toolbar density="compact">
        <CategoryMenu v-if="this.$route.name == `catalog`" />
        <v-btn 
          v-else
          @click="this.$router.push('/catalog')"
          color="primary"
          dark
        >
          Каталог
        </v-btn>
        <v-spacer></v-spacer>
        <div v-if="$store.state.jwt">
          {{$store.state.auth_user_name}}
          <v-btn @click="logout">
            Выйти
          </v-btn>
        </div>
        <div v-else>
          <v-btn @click="this.$router.push('/auth');">
            Войти
            <v-icon>mdi-account</v-icon>
          </v-btn>
        </div>
        
        <v-btn @click="this.$router.push('/basket');"
        >
            Корзина
            <v-icon>mdi-cart</v-icon>
            {{ basketSize }}
        </v-btn>
        </v-toolbar>
    </v-card>
</template>

<script>
import CategoryMenu from '@/components/CategoryMenu.vue';

export default {
  name: 'ToolbarView',
  components: {
    CategoryMenu
  },
  methods: {
    logout() {
      this.$store.commit('logout');
    }
  },
  computed: {
    basketSize() {
      return localStorage.getItem('basket') ? `(${JSON.parse(localStorage.getItem('basket')).length})` : "";
    }
  }
}
</script>

<style lang="scss">

</style>