**application.yaml** : là file cấu hình trung tâm của Spring Boot.
Trong file này, bạn có thể định nghĩa các thuộc tính cấu hình cho ứng dụng của mình, bao gồm cổng mà ứng dụng sẽ chạy, thông tin kết nối cơ sở dữ liệu, cấu hình logging, và nhiều thứ khác.
khi dự án được thực thi, Spring Boot sẽ tự động đọc file application.yaml và áp dụng các cấu hình được định nghĩa trong đó.

file **pom.xml** : là file cấu hình của Maven, được sử dụng để quản lý các dependencies (thư viện) mà dự án của bạn cần. 
Trong file này, bạn có thể định nghĩa các dependencies mà dự án của bạn sử dụng, cũng như các plugin và cấu hình build khác.
Khi bạn chạy lệnh Maven để xây dựng dự án, Maven sẽ đọc file pom.xml để biết những thư viện nào cần được tải về và sử dụng trong quá trình xây dựng và chạy ứng dụng.

annotation là metadata bản chất là “nhãn dán” để framework/compiler/runtime biết cần xử lý gì.
 có 4 loại annotation chính:
1) Theo nguồn gốc
   Java chuẩn: @Override, @Deprecated, @SuppressWarnings.
   Framework (Spring/JPA/Validation...): @Service, @Entity, @NotNull.
   Tự định nghĩa: bạn có thể tạo @MyAnnotation.
2) Theo mục đích dùng
   Compile-time: hỗ trợ compiler/annotation processor (vd Lombok @Getter).
   Runtime: framework đọc bằng reflection khi app chạy (Spring dùng rất nhiều).
   Documentation/Marker: đánh dấu thông tin.
3) Theo vị trí gắn (@Target)
   Gắn lên class, method, field, parameter, constructor...
4) Theo thời điểm giữ lại (@Retention)
   SOURCE: chỉ lúc compile.
   CLASS: trong .class nhưng runtime không đọc.
   RUNTIME: runtime đọc được (Spring cần cái này cho nhiều annotation).

| Annotation | Ý nghĩa dễ hiểu (Nó làm gì?) | Khi nào code thì nghĩ tới việc dùng nó? (Điều kiện kích hoạt) | Khi dùng cần truyền tham số gì vào? |
|---|---|---|---|
| `@AllArgsConstructor` | **Tự động viết code nạp đủ dữ liệu.** | Khi class có quá nhiều biến (field), lười viết hàm khởi tạo (constructor) bằng tay để nhét dữ liệu vào. | Không cần truyền gì. (Tự nó quét tất cả các biến đang có). |
| `@Bean` | **Nhờ Spring quản lý món đồ này giùm.** | Khi xài thư viện của người khác (như `RestTemplate`, `ModelMapper`) và muốn mang nó vào xài chung trong app của mình. | Thường không cần. Có thể truyền tên: `@Bean(name = "myBean")` nếu muốn gọi tên riêng. |
| `@Column` | **Mô tả chi tiết cột trong Database.** | Khi thiết kế Database, muốn ép luật cho cột đó (không được để trống, độ dài tối đa) hoặc khi tên biến Java khác với tên cột DB. | `name = "tên_cột"`, `nullable = false` (không rỗng), `length = 255`, `unique = true` (không trùng lặp). |
| `@CommentIdExists` | *(Tự chế)* **Kiểm tra ID Bình luận có thật không.** | Khi làm chức năng "Trả lời bình luận" hoặc "Xóa bình luận", cần rào trước xem cái bình luận gốc nó có tồn tại trong DB không. | `message = "Bình luận không tồn tại"` để báo lỗi cho Frontend. |
| `@Configuration` | **Đánh dấu bản vẽ cài đặt hệ thống.** | Khi tạo một file chuyên để setup những thứ cốt lõi (Ví dụ: File phân quyền Security, file cấu hình gửi Email). | Không cần truyền gì. |
| `@Constraint` | **Khai báo đây là quy tắc kiểm tra lỗi.** | Bắt buộc dùng khi bạn đang tự chế ra một cái nhãn dán như `@CommentIdExists` ở trên. | `validatedBy = ClassChứaLogicKiểmTra.class`. |
| `@ControllerAdvice` | **Trạm y tế tổng bắt mọi lỗi.** | Khi bạn lười viết khối `try...catch` ở từng API một. Muốn lỗi gì văng ra cũng gom về một phòng ban này xử lý. | Thường không cần. Dùng `basePackages = "com.abc"` nếu chỉ muốn bắt lỗi ở một thư mục cụ thể. |
| `@Convert` | **Lắp bộ chuyển đổi dữ liệu.** | Khi trong DB lưu kiểu số (0, 1) nhưng trong Java lại code theo kiểu chữ (ACTIVE, INACTIVE) cho dễ đọc. | `converter = ClassChứaLogicChuyểnĐổi.class`. |
| `@CreationTimestamp` | **Tự chốt ngày giờ tạo.** | Khi bảng nào cũng cần cột `created_at` (ngày tạo) mà bạn hay quên hoặc lười viết lệnh `new Date()`. | Không cần truyền gì. |
| `@CrossOrigin` | **Cấp giấy thông hành gọi chéo Web.** | Khi code Frontend (React/Vue) gọi API bị trình duyệt báo lỗi chữ đỏ chót "Blocked by CORS policy". | `origins = "http://localhost:3000"` (Tên miền được phép gọi API). |
| `@DeleteMapping` | **Cánh cửa nhận lệnh: "Xóa đi".** | Khi viết API cho chức năng "Xóa" một cái gì đó (Xóa user, xóa bài). | Đường dẫn API. VD: `@DeleteMapping("/delete/{id}")`. |
| `@Documented` | **In nhãn này vào tài liệu hướng dẫn.** | Khi viết thư viện đem bán hoặc cho team khác xài, muốn nhãn dán này hiện lên trên tài liệu code. | Không cần truyền gì. |
| `@Email` | **Kiểm tra chuẩn định dạng Email.** | Khi làm Form đăng ký/đăng nhập có ô nhập email, sợ user gõ bậy bạ thiếu chữ `@`. | `message = "Email sai định dạng"`. |
| `@EnableSpringDataWebSupport` | **Bật tính năng chia trang tự động.** | Khi làm danh sách sản phẩm dài, muốn cắt ra từng trang nhỏ (vd URL có `?page=1&size=10`). | Không cần truyền gì. |
| `@Entity` | **Đây là khuôn mẫu của bảng Database.** | Khi bắt đầu tạo một class để hứng và lưu dữ liệu tương ứng với một bảng SQL. | `name = "tên_thực_thể"` (ít dùng, thường xài `@Table`). |
| `@ExceptionHandler` | **Bác sĩ chuyên khoa chữa 1 loại bệnh.** | Phải nằm trong `@ControllerAdvice`. Khi muốn quy định: "Cứ gặp lỗi không tìm thấy (NotFound) thì nhảy vào hàm này xử lý". | `TênLỗi.class` (VD: `@ExceptionHandler(Exception.class)`). |
| `@GeneratedValue` | **Nhờ DB tự động bốc số thứ tự (ID).** | Đi kèm `@Id`. Khi lười tự gen mã ID, muốn MySQL/PostgreSQL tự động tăng từ 1, 2, 3... | `strategy = GenerationType.IDENTITY` (Đẩy việc tăng số cho DB làm). |
| `@GetMapping` | **Cánh cửa nhận lệnh: "Lấy ra cho xem".** | Khi viết API để hiển thị dữ liệu (Xem danh sách, xem chi tiết), không làm thay đổi Database. | Đường dẫn API. VD: `@GetMapping("/list")`. |
| `@Getter` | **Tự động viết hàm lấy dữ liệu.** | Khi có các biến `private` và muốn lấy dữ liệu của nó ra dùng ở class khác. | Không cần truyền gì. |
| `@Id` | **Đóng dấu Khóa chính (Căn cước).** | Bắt buộc phải có trong class `@Entity`. Để framework biết dòng nào phân biệt với dòng nào. | Không cần truyền gì. |
| `@JoinColumn` | **Gắn bảng chỉ đường liên kết.** | Khi 2 bảng có quan hệ với nhau (Ví dụ bảng Hóa Đơn cần biết ai mua nên phải có cột `user_id`). | `name = "tên_cột_khóa_ngoại"` ở dưới DB. |
| `@Length` | **Đặt luật: Chữ dài tối thiểu/tối đa.** | Khi làm Form đăng ký, bắt User tạo Mật khẩu phải từ 6 đến 20 ký tự. | `min = 6`, `max = 20`, `message = "Mật khẩu quá ngắn"`. |
| `@ManyToOne` | **Quan hệ "Nhiều con - Một bố".** | Đứng ở class Bình luận (Nhiều) nhìn sang class Bài viết (Một). | `fetch = FetchType.LAZY` (Mẹo: Luôn dùng LAZY để code chạy nhanh, không bị kéo lố dữ liệu). |
| `@Modifying` | **Báo động: Sắp sửa/xóa dữ liệu.** | Đi kèm `@Query`. Bắt buộc phải thêm vào khi bạn viết câu lệnh SQL bằng tay mà câu đó là UPDATE hoặc DELETE. | `clearAutomatically = true` (Dọn dẹp bộ nhớ đệm sau khi sửa). |
| `@NotBlank` | **Bắt buộc: Không được bỏ trống rỗng.** | Khi user nhập ô Tên, Mật khẩu, sợ nó cố tình gõ toàn dấu cách trắng (space) để lách luật. | `message = "Không được để trống"`. |
| `@Nullable` | **Nhắc nhở: Chỗ này có thể rỗng (null).** | Khi code một hàm mà tham số truyền vào có cũng được, không có cũng không sao. Nhắc anh em team cẩn thận. | Không cần truyền gì. |
| `@OnDelete` | **Luật: Bố chết thì Con tự hủy.** | Khi làm tính năng Xóa User, muốn hệ thống tự động xóa sạch Hóa Đơn của User đó trong DB mà không cần viết code vòng lặp. | `action = OnDeleteAction.CASCADE`. |
| `@OneToMany` | **Quan hệ "Một bố - Nhiều con".** | Đứng ở class Bài viết (Một) nhìn sang một List danh sách các Bình luận (Nhiều). | `mappedBy = "tên_biến_bên_class_con"`. |
| `@Override` | **Báo máy dò lỗi viết đè.** | Khi bạn viết lại một hàm đã có sẵn ở class Cha, sợ gõ sai chính tả tên hàm dẫn đến sai logic. | Không cần truyền gì. |
| `@Param` | **Gắn thẻ tên cho biến nhét vào SQL.** | Khi viết `@Query` có biến động (`WHERE name = :ten`), dùng nó để nối `:ten` với biến String truyền vào hàm. | `"tên_biến_trong_câu_SQL"`. |
| `@PathVariable` | **Lọc lấy tham số trực tiếp từ link web.** | Khi thiết kế đường dẫn API đẹp, giấu ID thẳng vào link (VD: [web.com/users/5](https://web.com/users/5) -> Số 5 là biến cần lấy). | `"tên_biến_trên_link"` (VD: `@PathVariable("id")`). |
| `@Pattern` | **Ép nhập theo chuẩn Regex (tự chế).** | Khi Form bắt nhập đúng Số điện thoại Việt Nam hoặc CMND có định dạng cực kỳ gắt gao. | `regexp = "^0[0-9]{9}$"` (Chuỗi quy tắc Regex). |
| `@PostIdExists` | *(Tự chế)* **Kiểm tra ID Bài viết có thật không.** | Giống `@CommentIdExists`, dùng khi làm chức năng "Tạo bình luận mới" để chặn user xạo chèn ID bài viết ảo. | `message = "Bài viết không tồn tại"`. |
| `@PostMapping` | **Cánh cửa nhận lệnh: "Tạo mới".** | Khi viết API nhận dữ liệu từ Form (như Đăng ký, Đăng bài) để nhét vào Database. | Đường dẫn API. VD: `@PostMapping("/create")`. |
| `@Primary` | **Gắn huy chương "Mặc định xài cái này".** | Khi bạn có 2 công cụ kết nối thanh toán (Momo và ZaloPay), muốn Spring ưu tiên tự lôi Momo ra xài nếu không ai chỉ định. | Không cần truyền gì. |
| `@PutMapping` | **Cánh cửa nhận lệnh: "Sửa và cập nhật lại".** | Khi viết API cho chức năng "Chỉnh sửa" thông tin tài khoản, cập nhật toàn bộ bài viết. | Đường dẫn API. VD: `@PutMapping("/update/{id}")`. |
| `@Query` | **Tự viết tay câu SQL.** | Khi cái framework Spring nó không tự đẻ ra được câu tìm kiếm quá phức tạp (Ví dụ: Nối 3-4 bảng, tính tổng doanh thu). | `"SELECT * FROM..."`, `nativeQuery = true` (Nếu muốn viết SQL thuần). |
| `@RequestBody` | **Gom đống dữ liệu JSON biến thành Object Java.** | Bắt buộc phải có ở các hàm POST/PUT khi Frontend gửi lên một cục JSON to đùng thay vì từng biến lẻ tẻ. | Không cần truyền gì. |
| `@ResponseStatus` | **Dập con dấu trạng thái cho kết quả.** | Khi code chạy đúng, thay vì trả về mã 200 OK chung chung, muốn chuẩn hóa trả về mã 201 (Tạo thành công), 204 (Xóa thành công). | `HttpStatus.CREATED`, `HttpStatus.NO_CONTENT`... |
| `@RestController` | **Quầy giao dịch tự động nhả JSON.** | Bắt đầu viết một file Controller cho API mới (Ví dụ: API quản lý User) giao tiếp với App điện thoại/ReactJS. | Không cần truyền gì. |
| `@Retention` | **Xác định tuổi thọ của nhãn dán.** | Bắt buộc dùng khi đang tạo custom annotation. Phải báo cho máy biết khi code chạy (runtime) nhãn này còn tác dụng không. | `RetentionPolicy.RUNTIME` (99% xài cái này). |
| `@Scope` | **Quy định đẻ ra 1 cái chung hay nhiều bản sao.** | Khi làm giỏ hàng (Cart). Giỏ hàng của ai nấy xài, không được dùng chung 1 cái Singleton mặc định của Spring. | `"prototype"` (Mỗi lần gọi là đẻ ra 1 cái mới), `"request"`, `"session"`. |
| `@Service` | **Đánh dấu phòng ban nghiệp vụ.** | Khi viết class xử lý logic cộng trừ nhân chia, tính toán thuế, băm mật khẩu... (Nằm giữa Controller và Repository). | Không cần truyền gì. |
| `@Setter` | **Tự động viết hàm sửa đổi dữ liệu.** | Khi cần sửa đổi giá trị bên trong của một Object từ bên ngoài. (Chú ý: Ít dùng cho `@Entity` để tránh bị sửa ID bậy bạ). | Không cần truyền gì. |
| `@SpringBootApplication`| **Nút khởi động lò phản ứng.** | Không bao giờ nghĩ tới việc dùng vì khi bấm tạo Project là Spring Boot đã tự nhét nó vào file Main sẵn rồi. | Thường không cần truyền gì. |
| `@SpringBootTest` | **Bật máy chủ mô phỏng để chạy Test.** | Khi viết code kiểm thử (Unit Test/Integration Test) mà cần chạy cả Database thật để xem câu truy vấn có đúng không. | `webEnvironment = WebEnvironment.RANDOM_PORT`. |
| `@Table` | **Chỉ định tên bảng Database.** | Khi tên class Java (`User`) khác hẳn hoặc dễ đụng chạm với từ khóa cấm trong SQL, muốn đặt tên bảng an toàn hơn (`tbl_users`). | `name = "tên_bảng_ở_dưới_DB"`. |
| `@Target` | **Luật vị trí dán nhãn.** | Bắt buộc dùng khi đang tạo custom annotation. Cấm người khác dán nhãn linh tinh không đúng chỗ. | `ElementType.FIELD` (Dán lên biến), `ElementType.METHOD` (Dán lên hàm). |
| `@Test` | **Đánh dấu đây là kịch bản chạy thử.** | Khi đang trong file Test, muốn báo cho máy tính biết "Hãy tự động chạy hàm này để check xem luồng code kia đúng hay sai". | Không cần truyền gì. |
| `@Transactional` | **Bật chế độ: Thành công hết hoặc Xóa bài làm lại.**| Khi một hàm có từ 2 thao tác DB trở lên (Ví dụ: Trừ tiền người gửi -> Cộng tiền người nhận). Lỗi ở giữa là phải lùi lại từ đầu. | `rollbackFor = Exception.class` (Gặp lỗi gì thì lùi lại). |
| `@UpdateTimestamp` | **Tự chốt ngày giờ chỉnh sửa.** | Đi chung với `@CreationTimestamp`, dùng cho cột `updated_at` để lưu lịch sử lần sửa đổi cuối cùng. | Không cần truyền gì. |
| `@Valid` | **Ra lệnh kích hoạt dàn máy quét lỗi.** | Dán ngay trước `@RequestBody`. Khi nhận cục data từ Frontend gửi lên, muốn lôi mớ luật `@NotBlank`, `@Email` ra kiểm tra ngay lập tức. | Không cần truyền gì. |
| `@Validated` | **Bản quét lỗi chia theo Nhóm.** | Khi 1 class User có chung luật nhưng API Tạo Mới bắt buộc nhập Password, còn API Cập Nhật thì cho phép để trống Password. | Tên của Interface tạo Nhóm. VD: `@Validated(OnCreate.class)`. |

`ConstraintValidator<PostIdExists, Long>` :Đây là interface của Bean Validation để viết custom validator.
Ý nghĩa generic:
PostIdExists: annotation custom bạn tự tạo.
Long: kiểu dữ liệu cần validate (id post).
Khi request vào controller có @PostIdExists Long postId, Spring Validation gọi validator này.
Validator thường check DB: nếu postId không tồn tại thì trả lỗi validation (400), không chạy tiếp service.

Dùng khi:
Rule không có sẵn (@NotBlank, @Email không làm được check tồn tại DB). 
Không cần dùng khi: Chỉ cần rule cơ bản có sẵn.

`JpaRepository<Post, Long>, JpaSpecificationExecutor<Post>` Đây là 2 interface repo kết hợp:
JpaRepository<Post, Long>
CRUD chuẩn: save, findById, findAll, deleteById...
Post là entity, Long là kiểu id.
JpaSpecificationExecutor<Post>
Thêm khả năng query động theo Specification (thêm nhiều điều kiện tìm kiếm phức tạp).
Dùng cho filter linh hoạt (nhiều điều kiện, build query runtime).
Kết hợp 2 cái = vừa CRUD nhanh, vừa lọc nâng cao tốt.

`ResponseEntityExceptionHandler` là Class base của Spring để xử lý exception web theo chuẩn.
Bạn có thể extends class này trong ErrorHandler để override các lỗi phổ biến:
validate fail
type mismatch
missing param
Mục tiêu: trả lỗi JSON thống nhất cho toàn API.
Khi nên dùng:
Muốn chuẩn hóa format lỗi toàn dự án. Khi không cần:
Dự án rất nhỏ, xử lý lỗi thủ công từng controller (nhưng sẽ rối nhanh).

`SecurityFilterChain` là Trái tim cấu hình Spring Security mới (thay cho WebSecurityConfigurerAdapter cũ).
Trong `SecurityConfiguration`, bean này định nghĩa:
bật/tắt csrf
cors
stateless session
rule quyền theo method/url
cơ chế auth (ở bạn là httpBasic)
Cách hiểu đơn giản:
Đây là “chuỗi cổng kiểm soát” mọi request trước khi vào controller.
Request phải đi qua chain này, đạt điều kiện mới được vào API.

luồng của 1 request khi có `SecurityFilterChain`:
**Client -> SecurityFilterChain -> Controller -> Validation -> Service -> Repository -> DB -> Service -> Controller -> Response**

Nếu không có Spring Security (không có SecurityFilterChain/không bật starter security), luồng sẽ là:
**Client -> DispatcherServlet -> Controller -> Validation -> Service -> Repository -> DB -> Service -> Controller -> Response**

DispatcherServlet là “lễ tân trung tâm” của Spring MVC.
Khi request HTTP vào app:
Nó nhận request trước (sau security filter nếu có).
Tìm controller/method phù hợp (@GetMapping, @PostMapping...).
Bind dữ liệu request vào tham số (@PathVariable, @RequestBody...).
Gọi method controller.
Nhận kết quả, convert sang HTTP response (JSON/status).
Nếu lỗi, chuyển cho cơ chế exception handler (@ControllerAdvice, ResponseEntityExceptionHandler).