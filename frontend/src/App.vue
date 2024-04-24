<template>
  <router-view :key="this.$route.fullPath"/>
</template>

<style lang="scss">
#app {
  background-color: $main_bcg_color;
  height: 100vh;
}
</style>
