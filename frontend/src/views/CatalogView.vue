<template>
  <div class="page">
    <Toolbar/>
    <Navigation/>
    <GoodList/>
  </div>
</template>

<script>
import Toolbar from '@/components/Toolbar.vue'
import GoodList from '../components/GoodList.vue'
import Navigation from '../components/Navigation.vue'

export default {
  name: 'CatalogView',
  components: {
    Toolbar, GoodList, Navigation
  }
}
</script>
