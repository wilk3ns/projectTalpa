# Project Talpa

**Project Talpa** is an unofficial package manager designed to manage third-party community addons for Microsoft Flight Simulator 2020. It simplifies the process of finding, installing, and updating addons by utilizing RSS feeds from various source websites and allows users to make informed decisions about which packages to install or update.

## Features

- **Automatic Addon Discovery**: Sources information from RSS feeds provided by addon websites.
- **User Decision Based Updates**: Updates local packages according to user preferences.
- **Simplified Management**: Makes managing third-party addons easy and efficient.

## Installation

To install Project Talpa, follow these steps:

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/wilk3ns/projectTalpa.git
    cd project-talpa
    ```

2. **Add Gradle run configuration**:
    ```sh
    desktopRun -DmainClass=MainKt --quiet
    ```

3. **Run the Application**

## Usage

### Adding an RSS Feed

1. Navigate to the **RSS Feeds** section in the application.
2. Click on **Add Feed** and enter the URL of the RSS feed you wish to add.
3. The application will automatically fetch and list available addons from the provided feed.

### Installing an Addon

1. Browse through the list of available addons fetched from the RSS feeds.
2. Select the addon you wish to install.
3. Click **Install** and follow the on-screen instructions.

### Updating an Addon

1. Navigate to the **Installed Addons** section.
2. Check for updates for the installed addons.
3. If updates are available, select the addons you wish to update and click **Update**.

## Configuration

Project Talpa allows customization through a configuration file. By default, the configuration file is located at `config.json`. You can modify this file to change various settings such as the default download directory, update frequency, and more.

## Contributing

We welcome contributions from the community! If you have a feature request, bug report, or would like to contribute code, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is under no license currently

## Contact

For any questions or suggestions, please contact me at [kamran@greatstuff.ee](mailto:kamran@greatstuff.ee).

---

**Project Talpa** is developed and maintained by Kamran Gurbanli. It is not affiliated with or endorsed by Microsoft or Asobo Studio.

Happy flying with Microsoft Flight Simulator 2020!
