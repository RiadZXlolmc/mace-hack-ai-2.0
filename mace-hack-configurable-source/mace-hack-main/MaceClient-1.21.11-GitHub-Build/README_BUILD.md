# Mace Client — GitHub build

This repository is set up so GitHub Actions builds the Fabric 1.21.11 mod for you.

## Build without installing Java or Gradle

1. Create a GitHub repository and upload all files from this folder.
2. Open the repository's **Actions** tab.
3. Select **Build Mace Client**.
4. Click **Run workflow**.
5. When the run finishes, open the run and download the **MaceClient-1.21.11** artifact.
6. Extract the downloaded artifact and put the `.jar` into your Minecraft `mods` folder.

The GitHub runner installs Java 21 and Gradle automatically; nothing needs to be installed on your PC for the build.

You still need the appropriate Fabric Loader/Fabric API installed in the Minecraft instance that will run the mod.
