*Environment & Tools
*Android Studio: Narwhal Feature Drop | 2025.1.2
*Library Versions
Dependency	Version
Android Gradle Plugin	8.12.0
Coil	2.7.0
Gson Converter	3.0.0
Core Testing	2.2.0
Glide	4.16.0
Koin (Android & Core)	4.1.0
Kotlin	2.2.0
Core KTX	1.16.0
JUnit	4.13.2
JUnit Version	1.3.0
Espresso Core	3.7.0
AppCompat	1.7.1
Kotlin Coroutines Test	1.10.2
Logging Interceptor	5.1.0
Material Components	1.12.0
Activity KTX	1.10.1
ConstraintLayout	2.2.1
MockK	1.14.5
Navigation Fragment KTX	2.9.3
Navigation UI KTX	2.9.3
SDP Android	1.1.1
Shimmer	0.5.0
SSP Android	1.1.1
Room KTX	2.7.2
Room Common JVM	2.7.2
Work Runtime KTX	2.10.3

*Functionality
-Fetch user data from a remote API and store it in a local Room database.
-If there is no internet connection, fetch data from the local database to ensure offline usability.
-When user status changes to Accepted or Rejected, the local database updates accordingly.
-If the device is online, the app syncs the updated status back to the server.
-If offline, the updated status is saved locally with a needSync flag for later synchronization.
-Currently, the sync logic for status updates uses a "needsync" update API (sync API for flags is not yet provided).
-When API data arrives, since flag update API is missing, the app checks local database flags to correctly display accepted or rejected status.

*Notes
This project requires Android Studio Android Studio: Narwhal Feature Drop | 2025.1.2 for compatibility.
The sync flag mechanism is implemented but depends on server API availability for full synchronization support.

*Final Output
<img width="235" height="490" alt="image" src="https://github.com/user-attachments/assets/665fc85b-2c1d-41fc-b4d7-f43694ddc53e" />

<img width="235" height="492" alt="image" src="https://github.com/user-attachments/assets/f262adc8-20e9-41e0-aeb3-f7b3ea268230" />

<img width="236" height="488" alt="image" src="https://github.com/user-attachments/assets/d9fcd802-839f-4ea0-add1-dc67ee84160b" />




