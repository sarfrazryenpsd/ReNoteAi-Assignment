# User Directory — Android Assignment

A simple Android app that fetches users from [JSONPlaceholder](https://jsonplaceholder.typicode.com/users) and displays them in a list with search, favorites, and offline support.

---

## How to Run

1. Clone the repository
2. Open in **Android Studio Meerkat** or later
3. Let Gradle sync complete
4. Run on a device or emulator with **API 24+**

No API keys or local configuration required.

---

## Architecture

The app follows **MVVM** with a clean separation across three layers:

```
UI Layer  →  Domain Layer  →  Data Layer
```

- **Data layer** — Retrofit fetches from the network; Room persists everything locally. The repository is the single source of truth.
- **Domain layer** — Plain Kotlin models and use cases. No Android or framework dependencies.
- **UI layer** — Composable screens observe `StateFlow` from ViewModels. Screens hold zero business logic.

### Offline-first approach

On first launch, data is fetched from the API and stored in Room. All screens read exclusively from the local database via `Flow`. The app remains fully functional after the initial load even without a network connection. Favorites are preserved across refreshes.

---

## State Management

Each screen has a `UiState` sealed interface with three states: `Loading`, `Success`, and `Error`.

- ViewModels expose state as `StateFlow`, collected in screens using `collectAsStateWithLifecycle` — which stops collection when the screen is not visible, preventing unnecessary work in the background.
- The user list screen separates `usersState`, `isRefreshing`, and `refreshError` into independent flows so a failed refresh never clears the displayed list.
- Search uses `debounce + flatMapLatest` on the query flow to filter Room results reactively without any extra network calls.

---

## Libraries

| Library | Reason |
|---|---|
| **Jetpack Compose** | Modern declarative UI toolkit; less boilerplate than XML |
| **Navigation Compose** | Type-safe navigation using `@Serializable` route objects |
| **Room** | Local persistence; `Flow`-based queries emit updates automatically |
| **Retrofit** | Industry-standard HTTP client with clean interface definitions |
| **kotlinx.serialization** | Kotlin-native JSON parsing; compile-time safe, no reflection |
| **Hilt** | Compile-time verified dependency injection; simplifies ViewModel and repository wiring |
| **OkHttp Logging Interceptor** | Inspect raw HTTP traffic during development |
| **Kotlin Coroutines + Flow** | Structured concurrency and reactive streams throughout the data pipeline |
| **Lifecycle Runtime Compose** | `collectAsStateWithLifecycle` for lifecycle-aware state collection |

---

## Assumptions

- The `/users` endpoint returns all fields needed for both the list and detail screens, so a separate `/users/{id}` call is never necessary.
- Favorites are stored locally only — they are not synced to any backend.
- The app targets API 24+ which covers ~97% of active Android devices.

---

## What I Would Improve Given More Time

- **Pagination** — the current endpoint returns 10 users which fits in memory, but a real endpoint would need paging via Paging 3.
- **Unit tests** — ViewModel and repository tests using `kotlinx-coroutines-test` and a fake repository implementation.
- **Better error granularity** — distinguish between no internet, server errors, and timeout at the UI level with different messages and icons.
- **ProGuard rules** — add explicit keep rules for kotlinx.serialization models for a production release build.
