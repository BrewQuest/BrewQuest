// Function to fetch user data from the database
async function fetchUserData() {
	try {
		const response = await fetch('your-user-api-endpoint');
		const userData = await response.json();
		return userData;
	} catch (error) {
		console.error('Error fetching user data:', error);
		return []; // Return an empty array or handle the error as needed
	}
}

// Function to fetch driver data from the database
async function fetchDriverData() {
	try {
		const response = await fetch('your-driver-api-endpoint');
		const driverData = await response.json();
		return driverData;
	} catch (error) {
		console.error('Error fetching driver data:', error);
		return []; // Return an empty array or handle the error as needed
	}
}

// Function to update the brewery leaderboard
async function updateBreweryLeaderboard() {
	const breweryTable = document.getElementById('breweryTable');
	const tbody = breweryTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	const userData = await fetchUserData();

	userData.forEach((entry, index) => {
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
}

// Function to update the passenger leaderboard
async function updatePassengerLeaderboard() {
	const passengerTable = document.getElementById('passengerTable');
	const tbody = passengerTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	const driverData = await fetchDriverData();

	driverData.forEach((entry, index) => {
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
}

// Update leaderboards initially and set an interval to update them periodically
updateBreweryLeaderboard();
updatePassengerLeaderboard();
setInterval(() => {
	// Update the leaderboards every 5 seconds (adjust as needed)
	updateBreweryLeaderboard();
	updatePassengerLeaderboard();
}, 5000);
