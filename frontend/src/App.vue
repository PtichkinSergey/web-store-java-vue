<template>
  <router-view :key="this.$route.fullPath"/>
</template>
<script>
export default{
  mounted() {
    if(localStorage.getItem('jwt') != 'null') {
      this.$store.dispatch('fetchAuthenticateUser');
    }
    if(localStorage.getItem('basket') != 'null') {
      this.$store.dispatch('fetchBasket');
    }
  }
}
</script>
<style lang="scss">
#app {
  background-color: $main_bcg_color;
  height: 100vh;
}
</style>
