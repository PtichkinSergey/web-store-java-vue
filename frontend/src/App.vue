<template>
  <router-view :key="$route.fullPath"/>
</template>

<style lang="scss">
#app {
  background-color: $main_bcg_color;
  height: 100vh;
}
</style>
