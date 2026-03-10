# Modul-1-Coding-Standards

## Reflection 1
Dalam pengerjaan modul ini, saya telah berusaha semaksimal mungkin untuk menerapkan prinsip clean code dan secure coding agar kode yang dihasilkan tidak hanya berfungsi, tetapi juga mudah dipahami dan aman. Modul praktikum yang telah diberikan sendiri sangat membantu untuk memberikan contoh implementasi clean code pada program ktia. Untuk mengupayakan clean code, saya menggunakan penamaan variabel yang deskriptif agar kode lebih mudah dibaca tanpa perlu menebak-nebak fungsinya, serta pembagian tanggung jawab yang jelas antar komponen menggunakan arsitektur MVC (Single Responsibility Principle). Dari sisi keamanan, saya berinisiatif menggunakan UUID untuk ID produk guna mencegah akses data yang tidak sah melalui enumerasi ID, serta memastikan input divalidasi dengan ketat. Meski demikian, saya menyadari kode ini belum sempurna, terutama pada penggunaan `ArrayList` di repository yang kurang efisien untuk pencarian data jika dibandingkan dengan `HashMap`. Hal ini dapat menghemat beban komputasi program kita apabila diimplementasikan. Selain itu, source code yang diberikan pada modul memanfaatkan dekorator @Getter dan @Setetr untuk model Product kita tanpa adanya metode validasi input user. Hal ini membolehkan user menginput kutantitas produk yang berjumlah negatif (tidak masuk akal). Saya pun menambahkan implementasi fungsi setter yang telah diperbarui untuk menangani kasus-kasus yang tidak diinginkan seperti kasus negatif. Terakhir, saya belum melihat adanya penggunaan basis data pada module ini. Saya sangat tertarik untuk melanjutkan program ini.

## Reflection 2
Setelah membuat unit test untuk fitur-fitur baru, saya merasa lebih lega karena logika program sudah teruji untuk berbagai skenario, baik positif maupun negatif. Kita pun sebagai pengembang tidak perlu repot-repot mengulang testing secara manual karena tes-tes tersebut sudah bisa diautomasi oleh test suite dan selenium.  Walaupun begitu, saya paham bahwa code coverage 100% bukanlah jaminan mutlak bahwa aplikasi bebas dari bug, karena kesalahan logika atau integrasi masih mungkin terjadi di luar jangkauan unit test. Contohnya, apabila saya membuat fungsi setter product quantity yang menerima input user, tetapi mengeset quantity product-nya menjadi 0 dan melakukan unit testing maka assert equals 0 dengan get product quantity akan mereturn true.

Tentang functional teset yang meminta "number of items in the product list," saya menyadari adanya duplikasi kode yang cukup mengganggu pada bagian konfigurasi driver dan setup awal. Hal ini jelas melanggar prinsip DRY, sehingga ke depannya saya berencana memperbaikinya dengan membuat kelas dasar terpisah untuk menangani konfigurasi tersebut agar kode pengujian menjadi lebih rapi dan mudah dirawat.

## Tambahan

Sebagai tambahan refleksi, sejujurnya saya masih sedikit kebingungan dengan funcitonal test edit product page saya dimana test tersebut kadang berhasil dan kadang tidak tergantung kecepatan internet saya. Saya sudah mencoba meresolusi hal ini dengan menambahkan sleep, tetapi problem tidak kunjung usai (kadang-kadang masih failed, kadang-kadang passed). Untuk modul sendiri sudah cukup baik. Satu masukan yang munkin dari saya sebagai mahasiswa adalah untuk memperjelas bagian bonus karena saya kesulitan memahami instruksi untuk mendapatkan bonus points pada modul ini

# Modul-2-CI-CD-DevOps

## Reflection 1

1.  **List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**

    I addressed a code quality issue regarding Field Injection in my ProductController and ProductServiceImpl classes, which was flagged by the code analysis tool. The usage of the @Autowired annotation directly on private fields is generally discouraged because it hides dependencies, makes the class tightly coupled to the Spring container, and complicates unit testing since the fields cannot be easily set without reflection. To fix this, I refactored the code to use Constructor Injection. I removed the @Autowired annotation from the fields and marked them as final to ensure immutability. Then, I created a constructor for the class that accepts the required dependencies as arguments. This strategy ensures that the dependencies are explicit and clearly defined at the time of object instantiation, which improves the testability and maintainability of the code.


2.  **Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**

    Yes, I believe the current implementation meets the definition of both Continuous Integration and Continuous Deployment. For Continuous Integration, the setup uses GitHub Actions workflows (ci.yml and scorecard.yml) to automatically build the project, run the test suite, and perform security analysis every time a commit is pushed or a pull request is created. This ensures that new code is consistently integrated and verified against the existing codebase to catch errors early. For Continuous Deployment, the integration with Koyeb utilizes a pull-based deployment strategy where the platform automatically detects changes in the main branch, builds the application using the Dockerfile, and deploys it to the production environment. This automates the release process, ensuring that the latest version of the software is always available to users without manual server configuration.

# Module 03 - Maintainability & OO Principles

## Reflection 1

### 1) Explain what principles you apply to your project!

I have applied all five SOLID principles as the design foundation to keep the codebase maintainable and extensible in this module's exercise.

Starting with the Single Responsibility Principle, each architectural layer is focused on a single role. ProductController and CarController are only responsible for HTTP routing and view binding, business logic lives entirely in the Service layer, and data persistence is handled by the Repository layer. I also removed unused imports and redundant code so that each class truly does only one thing.

For the Open/Closed Principle, the repository classes (CarRepository and ProductRepository) were converted into interfaces so that the Service layer depends on abstractions rather than concrete implementations. If a new storage mechanism like PostgreSQL is needed in the future, a new class like ProductRepositoryPostgresImpl can be created without touching the Service code at all.

Regarding the Liskov Substitution Principle, CarServiceImpl and ProductServiceImpl can substitute their respective interfaces without causing unexpected behavior. No method is improperly overridden or throws UnsupportedOperationException, meaning the behavioral contract of each interface is fully honored by its implementation.

For the Interface Segregation Principle, interfaces are designed to be domain-specific. Keeping Car and Product contracts separate prevents bloated interfaces, so each class only depends on the methods that are actually relevant to it.

Finally, for the Dependency Inversion Principle, the Service and Controller layers now depend on abstractions rather than concrete classes. CarController was also changed to inject CarService instead of CarServiceImpl directly, ensuring that high-level modules remain decoupled from low-level implementation details.

---

### 2) Explain the advantages of applying SOLID principles to your project with examples.

Applying SOLID has a tangible impact, especially on testability and decoupling between components.

From a testing perspective, constructor injection through interfaces makes unit testing significantly more straightforward. When writing tests for ProductServiceImpl or CarServiceImpl, I can inject Mockito mock objects directly via the constructor without needing to spin up the entire Spring ApplicationContext. This makes individual tests faster, more isolated, and easier to reason about.

From an extensibility standpoint, because the Service layer only knows the repository interface, swapping the storage mechanism from an in-memory list to an external database will not affect business logic at all. The change only needs to happen in one place, the new implementation class, without cascading modifications across the rest of the codebase.

---

### 3) Explain the disadvantages of not applying SOLID principles to your project with examples.

Ignoring SOLID makes code fragile and increasingly difficult to modify over time.

When the Service layer depends directly on concrete repository classes, even a minor change to a constructor or method signature forces cascading changes throughout the codebase. In the case of CarServiceImpl depending on CarRepository as a concrete class, modifying the repository structure would require updating the Service layer as well, significantly increasing the risk of introducing unintended bugs in code that should not have needed to change at all.

Beyond ripple effects, field injection also creates hidden dependencies that complicate testing. Without explicit constructor-declared dependencies, testing CarServiceImpl requires relying on reflection-based framework extensions, and in some cases even running the full Spring context, which slows down the entire test suite and undermines proper test isolation.

Lastly, violating SRP by merging routing, validation, and persistence logic into a single Controller class creates a situation where unrelated changes interfere with one another. A modification to the database schema could accidentally break the web view, and in a team setting, this kind of entanglement increases the frequency of merge conflicts since multiple contributors end up modifying the same file for entirely different reasons.

# Module 4 - Refactoring and TDD

## Reflection 1

### 1) Reflecting on Percival's (2017) Testing Objectives**

Applying Percival's principles of Correctness, Maintainability, and Productive Workflow, I found the TDD approach to be highly effective, though it demands a genuine change in how you think about development. In terms of correctness, the practice of writing failing tests first pushed me to precisely define expected behaviors and edge cases — such as triggering an IllegalArgumentException for invalid statuses — before any logic was written, which naturally discouraged cutting corners. From a maintainability standpoint, TDD made refactoring feel far less risky; when I swapped out hardcoded string statuses in favor of the OrderStatus Enum, the existing test suite immediately confirmed that nothing had broken, giving me real confidence in the structural changes I was making. As for workflow productivity, the RED phase does feel like it slows things down at first, but it more than pays off by reducing the time spent chasing down bugs later. One thing I want to improve going forward is taking more time upfront to plan test cases using techniques like Boundary Value Analysis, since I have a tendency to dive straight into writing tests without fully thinking through all the potential unhappy paths.

### 2) Reflecting on the F.I.R.S.T. Principles**

When I evaluated my unit tests for the Order domain against the F.I.R.S.T. principles, they held up well across the board, though I identified at least one area to work on. The tests are fast because they operate on isolated logic, bypassing the overhead of Spring contexts or database connections entirely, and they run in milliseconds as a result. Independence was achieved through the @BeforeEach setup method, which ensures every test begins from a clean slate with no risk of state leaking between cases. Repeatability was handled by using Mockito to mock the OrderRepository, making the tests fully deterministic and consistent across any environment. The tests are also self-validating, since strict assertions like assertEquals, assertThrows, and assertNull remove any need for manual inspection of print statements. Finally, I adhered to the Timely principle by writing each test immediately before its corresponding production code during the RED phase of TDD. The one area I want to refine is my use of Mockito's verify() method — relying too heavily on checking exact execution counts can accidentally bind a test to a specific implementation rather than its observable outcome, which makes future refactoring harder than it needs to be.

## Bonus 2 Reflection (Refactoring Partner's Code)

**1. Explain what you think about your partner’s code? Are there any aspects that are still lacking from your partner’s code?**
Secara keseluruhan, *code* yang ditulis oleh *partner* saya sudah cukup baik dalam mengimplementasikan fungsionalitas dasar seperti fitur penambahan *Payment* beserta *Service* dan *Repository*-nya. Namun, masih ada beberapa aspek yang kurang dari segi *Clean Code* dan prinsip SOLID. *Code* tersebut masih memiliki *code smells* dalam kategori **Couplers** berupa *tight coupling* akibat ketergantungan pada *concrete class* (melanggar *Dependency Inversion*) dan penggunaan *Field Injection*. Selain itu, terdapat juga kategori **Dispensables** (kode yang tidak berguna) berupa *dead code*, serta potensi **Change Preventers** akibat penggunaan *magic numbers*.

**2. What did you do to contribute to your partner’s code?**
Saya berkontribusi dengan melakukan *Code Review* pada *branch* `order` milik *partner* saya. Saya mengidentifikasi beberapa *code smells* yang secara langsung berdampak negatif pada *maintainability* (kemudahan pemeliharaan) dan *testability* kode. Setelah itu, saya membuat *branch* baru khusus untuk *refactoring* (`refactor/2406423055`), membersihkan *Dispensables*, mengurangi *Couplers*, memastikan semua *Unit Test* berjalan lancar tanpa *NullPointerException*, dan membuat *Pull Request* yang komprehensif kembali ke *branch* `order` beserta penjelasan detail terkait perbaikannya.

**3. What code smells did you find on your partner’s code? & 4. What refactoring steps did you suggest and execute to fix those smells?**

Daftar *code smells* yang saya temukan beserta langkah *refactoring* yang saya eksekusi dapat diamati sebagai berikut:

* **Magic Numbers & Magic Strings (Potensi Change Preventers):**
    * **Smell:** Terdapat *hardcoded value* seperti `"ESHOP"`, `16`, dan `8` di dalam *method* `isValidVoucherCode` pada `PaymentServiceImpl.java`. Hal ini sangat rawan memicu *Shotgun Surgery* (kategori **Change Preventers**) di mana perubahan satu aturan bisnis mengharuskan developer mencari dan mengubah angka-angka ini di berbagai tempat.
    * **Refactoring:** Saya mengekstrak nilai-nilai *hardcoded* tersebut menjadi konstanta `private static final` dengan nama yang representatif (contoh: `VOUCHER_PREFIX`, `VOUCHER_LENGTH`).
* **Redundant / Dead Code (Kategori Dispensables):**
    * **Smell:** Terdapat *method* `getAllPayments()` di `PaymentServiceImpl.java` yang isinya hanya memanggil *method* `getAllPayment()`. Ini adalah beban tak berguna (**Dispensables**) yang membuat *code* menjadi kotor (melanggar prinsip DRY).
    * **Refactoring:** Saya menghapus *method* `getAllPayments()` yang redundan tersebut sepenuhnya dan menyesuaikan pemanggilannya di dalam file *test*.
* **Field Injection Violation (Kategori Couplers):**
    * **Smell:** Dependensi di-injeksi langsung pada *field* menggunakan `@Autowired` di `PaymentServiceImpl.java`. Ini menciptakan *tight coupling* (**Couplers**) yang menyulitkan *Mocking* saat *Unit Testing* karena kita tidak bisa memasukkan dependensi tanpa bantuan *Spring Context*.
    * **Refactoring:** Saya mengubah *field* tersebut menjadi `final` dan mengimplementasikan *Constructor Injection* agar dependensi menjadi eksplisit.
* **Dependency Inversion Principle (SOLID) Violation (Kategori Couplers):**
    * **Smell:** Di dalam `ProductServiceImpl.java`, konstruktor bergantung pada *concrete class* `ProductRepositoryImpl` alih-alih abstraksinya (*Interface*). *Smell* **Couplers** tingkat tinggi ini menyebabkan *Mockito* gagal melakukan *inject mock* saat *testing*, sehingga memicu rentetan `NullPointerException`.
    * **Refactoring:** Saya mengubah tipe parameter pada konstruktor dan deklarasi *field* menjadi *interface* `ProductRepository` untuk mencapai *loose coupling*.
* **Inconsistent Naming Convention:**
    * **Smell:** Nama file *test* tidak sesuai dengan nama *class* di dalamnya (contoh: *file* `ProductServiceTest.java` berisi `class ProductServiceImplTest`). Ini melanggar konvensi penamaan standar di Java dan membingungkan *test runner* serta *developer* lain.
    * **Refactoring:** Saya me-*rename* file-file tersebut (menjadi `ProductServiceImplTest.java` dan `OrderServiceImplTest.java`) agar selaras secara eksak dengan nama *class*-nya.