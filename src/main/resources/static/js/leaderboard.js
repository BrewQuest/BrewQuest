// Sample data for the leaderboards
const breweryData = [
	{ username: 'Brewer1', score: 500 },
	{ username: 'Brewer2', score: 700 },
	{ username: 'Brewer3', score: 900 },
];

const passengerData = [
	{ username: 'Passenger1', score: 1000 },
	{ username: 'Passenger2', score: 800 },
	{ username: 'Passenger3', score: 600 },
];

// Function to update the brewery leaderboard
function updateBreweryLeaderboard() {
	const breweryTable = document.getElementById('breweryTable');
	const tbody = breweryTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	breweryData.forEach((entry, index) => {
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
function updatePassengerLeaderboard() {
	const passengerTable = document.getElementById('passengerTable');
	const tbody = passengerTable.getElementsByTagName('tbody')[0];
	tbody.innerHTML = ''; // Clear existing rows

	passengerData.forEach((entry, index) => {
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
