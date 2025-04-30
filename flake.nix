{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = {
    self,
    nixpkgs,
  }: let
    pkgs = nixpkgs.legacyPackages.x86_64-linux;
    libs = with pkgs; [
      ## native versions
      glfw3-minecraft
      openal

      ## openal
      alsa-lib
      libjack2
      libpulseaudio
      pipewire

      ## glfw
      libGL

      udev # oshi

      vulkan-loader # VulkanMod's lwjgl
    ];
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      packages = with pkgs; [corretto17];
      buildInputs = libs;
      LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath libs;
    };
  };
}
