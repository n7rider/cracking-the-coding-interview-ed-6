package n7rider.ch7.object_oriented_design;

import java.util.Map;

/**
 * File System: Explain the data structures and algorithms that you would use to design an in-memory
 * file system. Illustrate with an example in code where possible.
 *
 * After comparing:
 * Similar but the book stopped at Directory and Files. Didn't go exploring AddressMap and Disk
 */
public class Question7_11 {
    class Entity {
        String name;
    }

    abstract class File extends Entity {
        String fileSize;

        abstract File createFile(String dir, String fileName, byte[] data);
        abstract File readFile(String dir, String fileName);
        abstract File updateFile(String dir, String fileName, byte[] newData);
        abstract File appendFile(String dir, String fileName, byte[] additionalBytes);
        abstract void deleteFile(String dir, String fileName);

    }

    abstract class Directory extends Entity {
        Map<String, Entity> dirContentsByName;

        // CRUD Methods
    }

    abstract class AddressMap {
        Map<String, Long> fileLocation;
        Directory rootDir;
         abstract void findAndUpdateLocation(String size);
    }

    abstract class Disk {
        long totalStorage;
        long availableStorage;
        byte[] data;

        abstract void initDisk();
        abstract byte[] readLocation(long location, long bytes);
        abstract boolean writeLocation(long location, long bytes);
    }

}

/**
 * Components:
 * File
 * Directory
 * AddressMap
 * Disk
 *
 * Fields and methods:
 * File extends Entity
 *   fileName: string
 *   fileSize: long
 *   CRUD methods
 *
 * Directory extends Entity
 *   dirName: string
 *   dirContents: Entity[] // can be a map for faster search
 *   CRUD methods
 *
 * AddressMap // Singleton
 *   fileLocation: Map<String, long>
 *   findAndUpdateLocation(size)
 *
 *
 * Disk // Singleton for simplicity
 *   totalStorage: long
 *   availableStorage: long
 *   data: byte[]
 *   initDisk()
 *   readLocation()
 *   writeLocation()
 *   rootDir: Dir
 *
 *
 * Flow:
 *   initDisk()
 *     mount the disk
 *     set total storage, available storage
 *     set file system type
 *     init addressMap
 *
 *   createFile(String dir, String fileName, byte[] data)
 *     add entry to dirContents
 *     set file.FileName, file.FileSize
 *     addressMap.findAndUpdateLocation(data.size) // throw Exception if space is not found
 *     disk.writeToLocation(location, data)
 *     update availableStorage
 *
 *   readFile(String directory, String fileName)
 *     find file from directory.dirContents
 *     lookup addressMap.fileLocation (file.fileName)
 *     disk.readLocation(location, file.fileSize)
 *
 *   updateFile(..., byte[] newData)
 *     do the same as readFile
 *
 *     if(newData.size > oldData)
 *       if (addressMap.findAndUpdateLocation(fileName, additionalBytes)) // look for contiguous space for extra bytes or find a place to
 *     move the whole file to. If contiguous space is available, just return the current location
 *         disk.writeLocation(location, newData)
 *     update availableStorage
 *
 *
 *   appendFile(.., byte[] newBytes)
 *     do the same as readFile
 *     currLocation = addressMap.findAndUpdateLocation(fileName)
 *     if(addressMap.findAndUpdateLocation(fileName, additionalBytes) == currLocation)
 *       disk.writeLocation(currLocation + fileSize, newBytes)
 *     else
 *       disk.writeLocation(newLocation, currData + newBytes)
 *
 *   deleteFile(fileName)
 *     lookup addressMap for location
 *     update availableStorage
 *     addressMap.deleteEntry // we don't delete the storage, we just make the space available for writing
 *
 *
 *   CRUD of DIR
 *   createDir(String newDirName, String existingDir)
 *     existingDir.addDirContents(newDir)
 *
 *   readDir(String dirName) // dir name is the absolute path
 *     start from disk.dir
 *       navigate through sub dirs until the right dir is reached
 *
 *   updateDir(Dir dir, String newName, Entity[] newEntityToAdd)
 *     update dir.dirContents // same for delete
 *
 *
 *  AddressMap
 *    findAndUpdateLocation
 *      uses an internal map of location and available space:
 *        0, 10GB
 *        After 2 files added it becomes (0, 2.5GB), (4G, 2.GB). // Just an example. Actual file types might be more efficient
 *      Alternatively, create a map of available space and location
 *        e.g., 100MB, sector3G | 1G, sector 9.5G. // Can breakdown into multiple comopnents when a file is added
 *      goes through the map to find a space
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */