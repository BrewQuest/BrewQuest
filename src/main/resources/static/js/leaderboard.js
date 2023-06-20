// Function to update the brewery leaderboard
function updateBreweryLeaderboard() {
	const breweryTable = document.getElementById('breweryTable');
	const tbody = breweryTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	fetch('/?????????????')
		.then(response => response.json())
		.then(data => {
			data.forEach((entry, index) => {
				const row = document.createElement('tr');
				const usernameCell = document.createElement('td');
				const scoreCell = document.createElement('td');

				usernameCell.textContent = entry.username;
				scoreCell.textContent = entry.score;

				row.appendChild(usernameCell);
				row.appendChild(scoreCell);
				tbody.appendChild(row);

				// Add classes to the row
				if (index % 2 === 0) {
					row.classList.add('alternate-row');
				}
				row.classList.add('burlywood-text');
			});
		})
		.catch(error => {
			console.error('Error fetching brewery leaderboard:', error);
		});
}

// Function to update the passenger leaderboard
function updatePassengerLeaderboard() {
	const passengerTable = document.getElementById('passengerTable');
	const tbody = passengerTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	fetch('/?????????')
		.then(response => response.json())
		.then(data => {
			data.forEach((entry, index) => {
				const row = document.createElement('tr');
				const usernameCell = document.createElement('td');
				const scoreCell = document.createElement('td');

				usernameCell.textContent = entry.username;
				scoreCell.textContent = entry.score;

				row.appendChild(usernameCell);
				row.appendChild(scoreCell);
				tbody.appendChild(row);

				// Add classes to the row
				if (index % 2 === 0) {
					row.classList.add('alternate-row');
				}
				row.classList.add('burlywood-text');
			});
		})
		.catch(error => {
			console.error('Error fetching passenger leaderboard:', error);
		});
}

// Update leaderboards initially and set an interval to update them periodically
updateBreweryLeaderboard();
updatePassengerLeaderboard();
setInterval(() => {
	// Update the leaderboards every 5 seconds (adjust as needed)
	updateBreweryLeaderboard();
	updatePassengerLeaderboard();
}, 5000);


// the updateBreweryLeaderboard and updatePassengerLeaderboard functions have been updated to directly use the data retrieved from the Thymeleaf model. The users and drivers data are now retrieved from the Thymeleaf expressions [[${users}]] and [[${drivers}]], respectively.
//
// 	Make sure that the Thymeleaf expressions [[${users}]] and [[${drivers}]] match the attribute names used in your controller's viewLeaderboard method (i.e., "users" and "drivers").

