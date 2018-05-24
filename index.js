import { DeviceEventEmitter, NativeModules, Platform } from 'react-native'
const { DetectSoftNav } = NativeModules

const addListeners = ({ onShown, onHidden }) => {
    if (Platform.OS === 'ios') return

    DetectSoftNav.init()
    if (onShown) DeviceEventEmitter.addListener('softNavDidShow', onShown)
    if (onHidden) DeviceEventEmitter.addListener('softNavDidHide', onHidden)
  },
  isVisible = () =>
    new Promise(
      resolve =>
        Platform.OS === 'ios'
          ? resolve(false)
          : DetectSoftNav.isVisible(visible => resolve(visible))
    )

export default { addListeners, isVisible }
