# Fix KSP Processing Error in Room

The KSP processing error is caused by `SourceEntity` missing a `@PrimaryKey`. Room requires every entity to have at least one primary key.

## Proposed Changes

### Data Layer - Source Component

#### [MODIFY] [SourceEntity.kt](file:///Users/shyam/AndroidStudioProjects/AIPulse/app/src/main/java/com/example/aipulse/data/source/Room/SourceEntity.kt)
- Add an `id` field and mark it with `@PrimaryKey`.

#### [MODIFY] [mappers.kt](file:///Users/shyam/AndroidStudioProjects/AIPulse/app/src/main/java/com/example/aipulse/data/source/Mapper/mappers.kt)
- Update `toSourceEntity()` to map the `id` from `SourceItemDto` to `SourceEntity`.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify that the KSP processing error is resolved and the project builds successfully.
