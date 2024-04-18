<template>
    <div class="page">
        <Toolbar/>
        <v-card class="card">
            <h3>{{ $store.state.goods }}</h3>
        </v-card>
    </div>
</template>

<script>
  import Toolbar from '@/components/Toolbar.vue'
  export default {
    name: 'GoodView',
    components: {
      Toolbar
    },
    props: {
        good_id: -1
    }
  }
</script>

<style lang="scss" scoped>
.page {
  text-align: center;
}
.card {
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 15vw;
  border-radius: 15px;
  margin: 20px auto auto auto;
  padding: 20px;
}
</style>