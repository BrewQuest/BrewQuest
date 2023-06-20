const SearchBarInput = document.getElementById("SearchBar")
SearchBarInput.addEventListener("submit", function () {


    const x = SearchBarInput.value;

    let response = "_postal= 75001";

    if (postalCode) {
        response = "_postal=" + SearchBarInput;
    } else if (city) {
        response = "_city=" + SearchBarInput;
    } else if (state) {
        response = "_state=" + SearchBarInput;
    } else {
        alert('none found') // in the future change to 404 error
    }
    fetch('https://api.openbrewerydb.org/v1/breweries?by' + response, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(beerData => {
            body: JSON.stringify({
                name: name,
                address_1: address_1,
                city: city,
                state_province: state,
                postal_code: postalcode,
                phone: phone,
                website_url: website,
            })
        })
})






