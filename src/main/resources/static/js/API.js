const SearchBarInput = document.getElementById("SearchBar")

// SearchBarInput.addEventListener("submit", function () {
//     fetch("https://api.openbrewerydb.org/v1/breweries?by_city=san_diego&per_page=3")
//         .then(res => res.json())
//         .then(data => data.forEach(data))
//     {
//         const name = document.createElement('p');
//         name.textContent = data.name;
//         card.append(name);
//
//
//     }
// })



document.getElementById("Searchenter").addEventListener("submit", function () {
    let currentLocation = geocode(document.getElementById("SearchBarInput").value, MAPBOX_TOKEN);
    console.log(currentLocation);
    currentLocation.then(function (results) {
        map.flyTo({
            center: [results[0], results[1]],
            essential: true,
        });
    })
})
