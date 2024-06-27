<template>
  <router-view :key="this.$route.fullPath"/>
</template>
<script>
export default{
  mounted() {
    if(localStorage.getItem('jwt') != null) {
      this.$store.dispatch('fetchAuthenticateUser');
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
