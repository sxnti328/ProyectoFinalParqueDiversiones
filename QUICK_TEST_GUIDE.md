# Quick Test Guide - How to Run Tests

## 📋 Overview
- **Total Tests**: 132
- **Test Classes**: 9
- **Framework**: JUnit 5
- **Complexity**: Student Level (No Mocking Frameworks)

## 🚀 Running Tests in IntelliJ IDEA

### Method 1: Run All Tests
1. Open your project in IntelliJ
2. Right-click on the **`src/test/java`** folder
3. Select **"Run Tests in 'java'"** 
4. Watch the test runner window as all tests execute

### Method 2: Run Specific Test Class
1. Open the test file in editor (e.g., `VisitanteTest.java`)
2. Right-click on the class name
3. Select **"Run 'VisitanteTest'"**

### Method 3: Run Single Test Method
1. Open the test file
2. Right-click on the test method name (e.g., `testCrearVisitante`)
3. Select **"Run 'testCrearVisitante'"**

### Method 4: Run with Coverage
1. Right-click on `src/test/java`
2. Select **"Run 'Tests' with Coverage"**
3. IntelliJ will show code coverage percentage

## 🛠️ Running Tests via Command Line

### If Maven is Installed
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=VisitanteTest

# Run with output
mvn test -e -X

# Run and generate coverage report
mvn clean test jacoco:report
```

### If Gradle is Used
```bash
# Run all tests
gradle test

# Run specific test class
gradle test --tests VisitanteTest
```

## 📊 Test Breakdown by Class

| Test File | Tests | Purpose |
|-----------|-------|---------|
| **VisitanteTest** | 14 | Visitor creation, balance, tickets |
| **AtraccionTest** | 18 | Access validation, maintenance, climate |
| **OperadorTest** | 7 | Operator zones, management rights |
| **ZonaTest** | 19 | Zone operations, climate alerts |
| **TicketTest** | 13 | Ticket types, discounts, prices |
| **ParqueDeAtraccionTest** | 30 | Core park operations, CRUD, business rules |
| **VisitanteControllerTest** | 13 | Controller layer visitor operations |
| **OperadorControllerTest** | 15 | Operator business logic, balance reload |
| **AdminControllerTest** | 22 | Admin CRUD, zone/attraction management |
| **Total** | **132** | Full system coverage |

## ✅ What to Look For in Results

### Successful Test Run
```
Tests run: 132
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

### Common Issues

**Issue**: Tests don't compile
- **Solution**: Make sure JUnit 5 is in your pom.xml (Check your IDE has compiled the project)

**Issue**: `ClassNotFoundException` for test classes
- **Solution**: Right-click project → "Build" → "Rebuild Project"

**Issue**: Some tests fail
- **Solution**: Check if all source files compile without errors first

## 🧪 Test Categories

### 1. **Model Tests** (72 tests)
Classes tested directly without controllers:
- `VisitanteTest.java` - Balance and ticket logic
- `AtraccionTest.java` - Access control and maintenance
- `OperadorTest.java` - Zone assignment
- `ZonaTest.java` - Zone management
- `TicketTest.java` - Price calculations
- `ParqueDeAtraccionTest.java` - Core business logic

**Focus**: Data integrity, business rules

### 2. **Controller Tests** (60 tests)
Business logic layers:
- `VisitanteControllerTest.java` - Visitor CRUD
- `OperadorControllerTest.java` - Access validation, balance reload
- `AdminControllerTest.java` - Admin operations

**Focus**: Business workflows, integration

## 🎯 Key Business Rules Tested

1. ✓ **Balance Reload** - Only operators can reload visitor balance
2. ✓ **Access Validation** - Checks height, age, ticket, and attraction state
3. ✓ **Preventive Maintenance** - Automatic maintenance at 500 visitors
4. ✓ **Climate Alerts** - Water and high attractions close automatically
5. ✓ **Capacity Limits** - Park and zones respect visitor limits
6. ✓ **Duplicate Prevention** - No duplicate visitors/operators/zones
7. ✓ **Zone Authorization** - Operators only manage their zone
8. ✓ **Ticket Discounts** - Different ticket types with proper pricing

## 📖 Understanding Test Results

### Green (Passed) ✅
- All assertions succeeded
- Test is working correctly
- Business logic is sound

### Red (Failed) ❌
- An assertion failed
- Look at the error message to see which assertion failed
- Check the expected vs actual values

### Blue/Orange (Errors) ⚠️
- Exception was thrown
- Check the stack trace
- Usually indicates code compilation or configuration issue

## 🔍 How to Read a Test Method

```java
@Test
public void testRecargarSaldo() {
    // ARRANGE: Set up initial conditions
    double saldoAntes = visitante.getSaldoVirtual();  // Before: 150.0
    
    // ACT: Execute the action being tested
    String resultado = controller.recargarSaldo("44444", 50.0);
    
    // ASSERT: Verify the results
    assertTrue(resultado.contains("recargado"));       // Check message
    assertEquals(saldoAntes + 50.0, visitante.getSaldoVirtual());  // Check new balance
}
```

**Pattern**: Arrange → Act → Assert (AAA Pattern)

## 📝 Adding Your Own Tests

If you want to add more tests:

```java
@Test
public void testMyNewFeature() {
    // Arrange
    Visitante v = new Visitante("Test", "12345", 20, 1.70, 100.0);
    
    // Act
    v.recargarSaldo(50.0);
    
    // Assert
    assertEquals(150.0, v.getSaldoVirtual());
}
```

## 🐛 Troubleshooting

### Test Runs But Shows 0 Tests
- Right-click on test folder
- Select "Mark Directory as" → "Test Sources Root"
- Rebuild project

### Module Not Found Errors
- Check `module-info.java` has `opens` directives for test packages
- Or add `--add-opens org.example.proyectofinalparque=ALL-UNNAMED` to VM options

### Tests Pass Locally But Fail in CI
- Check Java version matches (should be 21)
- Check Maven/Gradle versions are correct
- Check all dependencies are in pom.xml

## 📚 Resources

- **JUnit 5 Documentation**: https://junit.org/junit5/docs/current/user-guide/
- **Test-Driven Development**: TDD improves code quality
- **Unit Testing Best Practices**: Keep tests simple and focused

## ✨ Best Practices Demonstrated in These Tests

1. **One assertion per test** ✓ (mostly)
2. **Clear test names** ✓ (testXXX describes what's tested)
3. **Setup/Teardown with @BeforeEach** ✓
4. **Independent tests** ✓ (no test depends on another)
5. **Real objects, no mocks** ✓ (student-friendly)
6. **Test both success and failure cases** ✓

---

**Ready to Run?**

Just right-click on `src/test/java` and select **"Run 'All Tests'"**!

**Expected Result**: 132 tests pass, 0 failures, BUILD SUCCESS ✅
