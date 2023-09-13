import UIKit
import SwiftUI
import ComposeApp

@main
struct iosApp: App {
    private var lifecycleHolder: LifecycleHolder { LifecycleHolder() }
    var body: some Scene {
        WindowGroup {
            ContentView(lifecycle: lifecycleHolder.lifecycle)
        }
    }
}

struct ContentView: View {
    private let lifecycle: LifecycleRegistry

    init(lifecycle: LifecycleRegistry) {
        self.lifecycle = lifecycle
    }
    var body: some View {
        ComposeView(lifecycle: lifecycle).ignoresSafeArea(.keyboard)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    private let lifecycle: LifecycleRegistry

    init(lifecycle: LifecycleRegistry) {
        self.lifecycle = lifecycle
    }

    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController(
            lifecycle: lifecycle
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
