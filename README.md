# Salon Management System

A Java-based appointment management system designed for salon operations. This application supports two user modes: **Admin** and **Client**, each with distinct functionalities for managing salon appointments efficiently.

## Features

### Admin Mode
- **Delete Appointments**: Remove scheduled appointments from the system
- **View All Appointments**: Display a complete list of all scheduled appointments
- **View Available Time Slots**: Check which time slots are available for booking

### Client Mode
- **Book Appointments**: Create new appointment reservations
- **Cancel Appointments**: Remove personal appointment bookings
- **View Appointment Details**: Check details of scheduled appointments

## Appointment Types

The system supports the following appointment types:
- **Haircut** - 40 minutes
- **Hair Coloring** - 60 minutes
- **Special Treatment** - 100 minutes
- **Quick Trim** - 30 minutes

## Project Structure

```
src/
├── Main.java                 # Entry point and main menu logic
├── Admin.java               # Admin user implementation
├── Client.java              # Client user implementation
├── User.java                # User interface
├── Appointment.java         # Appointment data model
├── AppointmentManager.java  # Appointment operations logic
├── AppointmentType.java     # Enum for appointment types
├── FileHandler.java         # File I/O operations
├── InvalidTimeException.java # Custom exception for time conflicts
├── UserException.java       # Custom exception for user-related errors
├── appointments.csv         # Appointments storage file
└── users.csv               # User credentials storage file
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line or terminal access

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd SalonManagementSystem
```

2. Compile the Java files:
```bash
javac src/*.java
```

### Running the Application

#### Admin Mode
```bash
java -cp src Main -a
```
You will be prompted for admin credentials.

#### Client Mode
```bash
java -cp src Main -c
```
You will be prompted for client credentials.

## Usage

### Admin Workflow
1. Run the application in admin mode with `-a` flag
2. Enter your admin credentials
3. Select from available operations:
   - View all scheduled appointments
   - Delete a specific appointment
   - Check available time slots
4. Follow the on-screen prompts

### Client Workflow
1. Run the application in client mode with `-c` flag
2. Enter your client credentials
3. Select from available operations:
   - Book a new appointment
   - Cancel an existing appointment
   - View appointment details
4. Follow the on-screen prompts

## Data Storage

The application uses CSV files for persistent data storage:

- **users.csv**: Stores user credentials and user type (Admin/Client)
- **appointments.csv**: Stores all scheduled appointments with details

## Exception Handling

- **InvalidTimeException**: Thrown when attempting to book a time slot that is already occupied
- **UserException**: Thrown for authentication errors or invalid user role access

## Class Overview

| Class | Purpose |
|-------|---------|
| `Main` | Application entry point and menu navigation |
| `AppointmentManager` | Core business logic for appointment operations |
| `FileHandler` | Manages reading/writing to CSV files |
| `Appointment` | Represents a single appointment |
| `User` | Interface for user types (Admin, Client) |
| `Admin` | Admin user with management privileges |
| `Client` | Client user with booking capabilities |
| `AppointmentType` | Enum defining available appointment types |

## Technologies Used

- **Language**: Java
- **Data Storage**: CSV files
- **Architecture**: Command-line application with multi-tier design

## Future Enhancements

- GUI implementation using Swing or JavaFX
- Database integration (MySQL, PostgreSQL)
- Email notifications for appointment confirmations
- Recurring appointments
- Resource/staff scheduling
- Payment integration
- Advanced reporting and analytics

## License

This project is provided as-is for educational purposes.

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.
