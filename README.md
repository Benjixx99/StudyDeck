# Study Deck

<p align="center">
  <img src="https://raw.githubusercontent.com/Benjixx99/StudyDeck/master/app/src/main/res/drawable/logo.png" width="64" alt="Study Deck logo" style="vertical-align:middle;">
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Room-3DDC84?style=for-the-badge&logo=sqlite&logoColor=white" alt="Room">
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle">
  <a href="https://github.com/Benjixx99/StudyDeck/releases/tag/v.1.0.0-beta.4">
    <img src="https://img.shields.io/badge/release-v.1.0.0--beta.4-00aced?style=for-the-badge" alt="Release v.1.0.0-beta.4">
  </a>
  <a href="https://github.com/Benjixx99/StudyDeck/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="License: MIT">
  </a>
</p>

Study Deck is a lightweight Android flashcard app that helps you learn new topics efficiently. It prevents forgetting by using spaced repetition and distraction‑free study sessions so you retain information longer, while making it simple to add cards and start studying.

## Features

- **Create & Manage Decks** – Build custom flashcard decks for any topic
- **Focused Study Mode** – Clean, distraction-free interface for efficient learning
- **JSON Import/Export** – Share decks or backup your data as JSON files
- **Local Data Persistence** – Store all cards locally using Room (SQLite)
- **Modern Declarative UI** – Built entirely with Jetpack Compose

## Technologies

| Technology      | Purpose                                |
| --------------- | -------------------------------------- |
| Kotlin          | Primary programming language           |
| Jetpack Compose | Modern declarative UI framework        |
| Room            | SQLite object-mapping library          |
| SQLite          | Local database storage                 |
| JSON            | Data import/export format              |
| Gradle          | Build system and dependency management |

## Getting Started

### Prerequisites

To run this project, you'll need:

- Android Studio (latest stable version)
- Kotlin 1.8+ support
- Android SDK 29+ (API level 29 or higher, Android 10+)
- A device or emulator running Android 10+ (API 29+)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Benjixx99/StudyDeck.git
   ```

2. Open the project in Android Studio.

3. Wait for Gradle to sync all dependencies.

4. Build and run the app:
   - Click **Build > Make Project**
   - Select a device/emulator
   - Click **Run > Run 'app'**

## Usage

1. **Create a deck**
   - Tap the "+" button to add a new deck.
   - After creating, the app opens the **Settings** tab for that deck: set **Name**, **Description**, **Color**, **Learn both sides** (front/back), and **What happens by failing** (how cards move between levels when answered “unknown”).

2. **Add cards**
   - Switch to the **Cards** tab and tap the "+" button.
   - For each side choose a **type**: **Text** or **Audio**, then provide the corresponding content.
   - Both front and back must be filled before saving the card.
   - New cards are added to the **first level** automatically.

3. **Add levels**
   - Open the **Levels** tab. One level (Interval = 1 day) exists by default.
   - Add levels with "+" — give each a **name** and an **interval** (number + unit: day, week, month, year).
   - Example: "Daily" → 1 day, "Weekly" → 1 week, "Monthly" → 1 month.

4. **Study**
   - Go to the **Learn** tab and choose a learning mode:
     - **Random learning:** all cards in the deck are presented in random order.
     - **Level learning:** pick a level that contains cards; only cards that are currently at this level are presented.
   - During learning, mark cards as **Known** or **Unknown**. The card’s next level is adjusted according to the deck’s Settings:
     - If **Known** → card moves to the next higher level.
     - If **Unknown** → behavior depends on the deck setting: move to first level, move down one level, or stay in current level.

6. **Import / Export**
   - Use the menu to export decks as JSON (for backup/sharing) or import compatible JSON files.

**Tips**
- Fill several cards and at least two levels before starting level-based learning to see progression.
- Use audio sides for pronunciation or listening practice; use text for definitions/answers.

## Contact

If you want to contact me, send an e-mail to:   [anon_writer@use.startmail.com](mailto:anon_writer@use.startmail.com)

Made with ❤️ by Benjixx99
