<template>
    <div class="card" id="comments_card">
    <h3 id="comments_header">Комментарии</h3>
    <div v-if="$store.state.jwt">
        <CommentForm/>
    </div>
    <div v-else>
        <i>Чтобы оставить комментарий к товару необходимо авторизоваться!</i>
    </div>
    <v-list 
        id="comments_list"
        v-if="$store.state.comments.length > 0"
    >
        <v-list-item
            v-for="comment of $store.state.comments"
            :key="comment.id"
        >
            <div id="comment_item">
                <div id="comment_author">
                    <v-avatar color="info">
                        <v-icon icon="mdi-account-circle"></v-icon>
                    </v-avatar>
                    <h3>{{ comment.author }}</h3>
                </div>
                <div id="comment_text">
                    <p>{{ comment.text }}</p>
                </div>
                <div id="rating">
                    <p>Оценка: {{ comment.rating }} / 10</p>
                </div>
            </div>
        </v-list-item>
    </v-list>
    </div>
</template>

<script>
    import CommentForm from './CommentForm.vue';
    export default {
        name: "CommentList",
        data: () => ({
            
        }),
        components: {
            CommentForm
        },
        props: {
            good_id: {
                type: Number,
                required: true
            }
        },
        methods: {
            async fetchComments() {
                this.$store.dispatch('fetchComments', this.good_id);
            },
        },
        mounted() {
            this.fetchComments();
        }
    }
</script>

<style lang="scss" scoped>
#comments_card {
    width: 60vw;
    text-align: left;
}
#comment_text {
    display: flex;
    flex-direction: column;
    text-align: left;
}

#comment_text_area {
   width: 30vw; 
}

#rating {
    width: 10vw;
}

#comment_author {
    width: 15vw;
    display: flex;
    text-align: left;
    align-items: center;
}
#comments_list {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    background-color: $comment_bcg_color;
}
#comment_item {
    width: 55vw;
    display: flex;
    align-items: center;
    flex-direction: row;
    justify-content: space-between;
    border: 1px solid;
    border-radius: 5px;
    padding: 20px;
}
#comments_header {
    text-align: left;
}
</style>