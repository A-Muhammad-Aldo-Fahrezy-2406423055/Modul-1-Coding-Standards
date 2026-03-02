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