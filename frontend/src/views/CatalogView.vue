<template>
  <div class="page">
    <Toolbar/>
    <GoodList/>
  </div>
</template>

<script>
import Toolbar from '@/components/Toolbar.vue'
import GoodList from '../components/GoodList.vue'

export default {
  name: 'CatalogView',
  components: {
    Toolbar, GoodList
  }
}
</script>
