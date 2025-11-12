# NexusSearch ✨

An AI-Powered Conversational Information Retrieval System built with Spring Boot and the Google Gemini & Search APIs.


---

### 🔴 Live Demo

**(Link to your deployed Render app will go here)**

---

### 💡 Core Features

* **Conversational Interface:** A sleek, "glassmorphism" chat UI with an animated "falling stars" background.
* **AI-Powered NLU:** Uses the Gemini API to understand natural language queries.
* **Dual-API Backend:**
    1.  **Google Search API:** First, it performs a real-time Google search to find *verified, working links* for articles and PDFs, preventing AI hallucinations.
    2.  **Gemini API:** Second, it feeds these verified links to Gemini for summarization, ensuring all sources are accurate.
* **Multi-Modal Retrieval:**
    * **Articles/PDFs:** Provides summaries with direct, clickable links.
    * **Images:** Falls back to Gemini's native grounding to find and display images directly in the chat.

### 🛠 Tech Stack

* **Backend:** Spring Boot (Java)
* **Frontend:** Thymeleaf (Server-Rendered HTML)
* **AI Engine:** Google Gemini API (`gemini-2.5-flash`)
* **Search Engine:** Google Programmable Search Engine API
* **Styling:** Custom CSS & [marked.js](https://marked.js.org/) (for Markdown rendering)
* **Deployment:** Render

---

### 💻 Running Locally

To run this project on your local machine, you will need to set up three API keys.

#### 1. Clone the Repository
```bash
git clone [https://github.com/your-username/NexusSearch.git](https://github.com/your-username/NexusSearch.git)
cd NexusSearch```

### 💻 Running Locally

To run this project on your local machine, you will need to set up three API keys.

#### 1. Clone the Repository
```bash
git clone [https://github.com/your-username/NexusSearch.git](https://github.com/your-username/NexusSearch.git)
cd NexusSearch```

### 💻 Running Locally

To run this project on your local machine, you will need to set up three API keys.

#### 1. Clone the Repository
```bash
git clone [https://github.com/your-username/NexusSearch.git](https://github.com/your-username/NexusSearch.git)
cd NexusSearch
```

#### 2. Obtain API Keys

You need to get **three** keys in total:

1.  **Gemini API Key:**
    * Go to [Google AI Studio](https://aistudio.google.com/app/apikey).
    * Create a new API key.
    * This is your `GOOGLE_API_KEY`.

2.  **Google Search API Key:**
    * Go to the [Google Cloud Console](https://console.cloud.google.com/apis/library).
    * Enable the **"Custom Search API"**.
    * Go to "Credentials" and create a new **API Key**.
    * This is your `Google Search_API_KEY`.

3.  **Search Engine ID (CX):**
    * Go to the [Programmable Search Engine](https://programmablesearchengine.google.com/controlpanel/all) dashboard.
    * Create a new search engine.
    * Select **"Search the entire web"**.
    * Find and copy the **"Search engine ID"** (CX).
    * This is your `Google Search_ENGINE_ID`.

#### 3. Set Environment Variables

You must set these three variables in the terminal you use to run the app.

**On Windows (PowerShell):**
```powershell
$env:GOOGLE_API_KEY="YOUR_GEMINI_KEY"
$env:GOOGLE_SEARCH_API_KEY="YOUR_GOOGLE_SEARCH_KEY"
$env:GOOGLE_SEARCH_ENGINE_ID="YOUR_CX_ID"
```

**On macOS/Linux (bash/zsh):**
```bash
export GOOGLE_API_KEY="YOUR_GEMINI_KEY"
export GOOGLE_SEARCH_API_KEY="YOUR_GOOGLE_SEARCH_KEY"
export GOOGLE_SEARCH_ENGINE_ID="YOUR_CX_ID"
```

#### 4. Build and Run

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
Your application will be available at `http://localhost:8080`.
