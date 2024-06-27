<template>
  <div class="page">
    <Toolbar/>
    <div id="navbar">
      <div id="navbar_path">
        <Navigation/>
      </div>
      <div id="navbar_switch">
        <SortSwitch/>
      </div>
    </div>
    <GoodList/>
  </div>
</template>

<script>
import Toolbar from '@/components/Toolbar.vue'
import GoodList from '../components/GoodList.vue'
import Navigation from '../components/Navigation.vue'
import SortSwitch from '../components/SortSwitch.vue'

export default {
  name: 'CatalogView',
  components: {
    Toolbar, GoodList, Navigation, SortSwitch
  }
}
</script>

<style>
#navbar {
  display: flex;
}
#navbar_path {
  align-self: flex-start;
}
#navbar_switch {
  align-self: flex-end;
}
</style>
