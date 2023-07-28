import SwiftUI
import shared

struct ContentView: View {
    private let torchController = TorchController()
    
    @ObservedObject private var torchEnabledCollector = TorchEnabledCollector()
    @State private var isVisibleTorchError = false
    
    init() {
        torchController.isEnabledFlow.collect(collector: torchEnabledCollector, completionHandler: { error in
            print("torchEnabledCollector ERROR: \(String(describing: error))")
        })
    }
    
    var body: some View {
        VStack {
            Text("Torch Enabled: \(String(describing: torchEnabledCollector.isEnabled))")
            
            Button("Turn On", action: {
                do {
                    try torchController.turnOn()
                } catch {
                    isVisibleTorchError = true
                    print("turn-on ERROR: \(String(describing: error))")
                }
            })
            
            Button("Turn Off", action: {
                do {
                    try torchController.turnOff()
                } catch {
                    isVisibleTorchError = true
                    print("turn-off ERROR: \(String(describing: error))")
                }
            })
        }.alert(isPresented: $isVisibleTorchError) {
            Alert(
                title: Text("ERROR: TORCH NOT FOUND"),
                message: Text("This device does not have a camera with a flash."),
                dismissButton: .default(Text("OK"))
            )
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

class TorchEnabledCollector: Kotlinx_coroutines_coreFlowCollector, ObservableObject {
    @Published var isEnabled = false
    
    @MainActor func emit(value: Any?) async throws {
        isEnabled = value as? Bool? == true
    }
}
