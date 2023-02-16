package com.artemzu.githubapp.repositories.paging

class RepositoriesPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key, isNewRequest: Boolean) -> Unit
) {

    private var currentKey: Key = initialKey
    private var isMakingRequest = false

    suspend fun loadNextItems(isNewRequest: Boolean) {
        if (isMakingRequest) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }

        currentKey = getNextKey(items)
        onSuccess(items, currentKey, isNewRequest)
        onLoadUpdated(false)
    }

    fun reset() {
        currentKey = initialKey
        isMakingRequest = false
    }
}
