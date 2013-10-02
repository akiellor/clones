(ns clones.cpu-spec
  (:require [speclj.core   :refer :all]
            [clones.memory :refer :all]))

(def mounts [])

(describe "Mountable memory"
  (describe "mount-read"
    (it "should translate the address passed to the device's read function to be relative to the mount point"
      (let [new-mounts (mount-device mounts 0x1000 0x1999 (fn [addr] addr) 'write-fn)]
        (should= 0 (mount-read new-mounts 0x1000))))

    (it "should raise an error trying to read from a location with no device"
      (should-throw Error "No device is mounted to handle 0x0000, current devices ()"
        (mount-read mounts 0x0000))))

    (it "should read from the correct mount point"
      (let [new-mounts (mount-device mounts 0x0000 0x0999 (fn [addr] :read-byte) 'write-fn)]
        (should= :read-byte (mount-read new-mounts 0x0001)))))

  (describe "mount-find"
    (it "should return nil if no mount matches the address"
      (should-be-nil (mount-find mounts 0x0)))

    (it "should return the io functions when the addr is in its range"
      (let [new-mounts (mount-device mounts 0x0000 0x0999 'read-fn 'write-fn)]
        (should= (mount-find new-mounts 0x100) {:start 0x0000 :end 0x0999 :read 'read-fn :write 'write-fn}))))

  (describe "mount-device"
    (it "should raise an error trying to mount to a range that already has a device"
      (let [new-mounts (mount-device mounts 0x0000 0x0999 'read-fn 'write-fn)]
        (should-throw Error "Device already mounted, current devices ({:end 2457, :start 0})"
          (mount-device new-mounts 0x0999 0x5000 'read-fn 'write-fn))))

    (it "should mount a device with a start and end memory point"
      (let [new-mounts (mount-device mounts 0x0000 0x0999 'read-fn 'write-fn)]
        (should= new-mounts '({:start 0x0000 :end 0x0999 :read read-fn :write write-fn})))))
