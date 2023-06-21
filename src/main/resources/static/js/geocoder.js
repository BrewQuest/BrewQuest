function geocode(search, token) {
    // api url
    var baseUrl = 'https://api.mapbox.com';
    // the end pint when I request lng and alt based on location name.
    var endPoint = '/geocoding/v5/mapbox.places/';
    // this fetch takes in a url endpoint that will return my lng and lat
    return fetch(baseUrl + endPoint + encodeURIComponent(search) + '.json' + "?" + 'access_token=' + token)
        .then(function(res) {
            return res.json();
            // to get all the data from the request, comment out the following three lines...
        }).then(function(data) {
            // console.log("line 27: " + data);
            return data.features[0].center;

        });
}