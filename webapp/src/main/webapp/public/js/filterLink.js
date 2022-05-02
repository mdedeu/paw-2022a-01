function resetAllFilters(){
    document.getElementById("reset").classList.add("hidden")
    document.getElementById("coin").options[0].selected = true
    document.getElementById("pm").options[0].selected = true
    document.getElementById("price").value = 0;

    var searchParams = new URLSearchParams(window.location.search)
    for(const [key] of searchParams){
        searchParams.delete(key)
    }
    searchParams.delete(id);
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;
}
function addPageValue(value){
    var searchParams = new URLSearchParams(window.location.search)
    searchParams.set("page",value);
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    window.location.href = newRelativePathQuery;
}
function addQueryParam(id) {
    var searchParams = new URLSearchParams(window.location.search)
    searchParams.set(id, document.getElementById(id).value);
    searchParams.delete("page")
    var newRelativePathQuery = window.location.pathname + '?' + searchParams.toString();
    history.pushState(null, '', newRelativePathQuery);
    document.getElementById("link").href = newRelativePathQuery;
    document.getElementById("reset").classList.remove("hidden");
}
