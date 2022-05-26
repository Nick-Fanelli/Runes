package engine.asset;

import engine.state.State;

public record Asset<AssetType>(AssetType rawAsset, State state) {}