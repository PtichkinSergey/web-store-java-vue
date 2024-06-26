<template>
    <div v-if="$store.state.categories.length > 0">
        <div id="navigation">
            <a 
                href="/catalog"
                class="link"
            >
                Главная
            </a>
            <div
                v-for="ctg in $store.getters.getPathToCategory(this.$route.query.category)"
            >
                /
                <a 
                    class="link"
                    @click="this.$router.push({query: {category: ctg.id}});"
                >
                    {{ ctg.name }}
                </a>
            </div>
        </div>
        <h3 class="page_title">
            {{ $store.getters.getCategoryById($route.query.category) }} ({{ $store.getters.getGoodsCountByCategory($route.query.category) }})
        </h3>
    </div>
</template>

<script>
    export default {

    }
</script>

<style>
#navigation {
  margin: 20px 0px 0px 20px;
  text-align: left;
  display: flex;
  align-items: baseline;
}

</style>