//
//  LifecycleHolder.swift
//  iosApp
//
//  Created by Fidriyanto Rizkillah on 12/09/23.
//

import Foundation
import ComposeApp

class LifecycleHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
