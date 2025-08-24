# PatientRegistration – REST API (Spring Boot)

Bu proje, **Java + Spring Boot** kullanılarak geliştirilmiş bir **RESTful Hasta Kayıt Sistemi**dir. Uygulama, **Nesne Yönelimli Programlama (OOP)** prensiplerine göre **katmanlı mimari** ile tasarlanmıştır.

## 📦 Genel Bilgi

- **Geçici Depolama:** Bellek içi `HashMap<String, Patients>`
- **Kalıcı Depolama:** `patients.txt` adlı düz metin dosyası (her satır bir JSON obje)

## 🔁 Uygulama Akışı

1. `load` – `patients.txt` dosyasından veriler belleğe alınır  
2. CRUD işlemleri – Hasta ekle, sil, güncelle, listele  
3. `save` – HashMap’teki veriler `patients.txt` dosyasına yazılır ve HashMap temizlenir  

> `save` sonrası veri bellekte tutulmaz, devam etmek için tekrar `load` yapılmalıdır veya yeni kayıt eklenmelidir.

## 🗂️ Katmanlar

```
web/controller     → HTTP isteklerini karşılar, PatientsService'e yönlendirir
application        → İş mantığını yürütür, HashMap ve dosya I/O (FileService, PatientJsonCodec)
domain/model       → Patients varlık modeli
```

## 🧬 Patients Modeli

```json
{
  "id": "UUID",
  "name": "string",
  "surname": "string",
  "age": 0,
  "phone": "string",
  "email": "string"
}
```

> `id`, `UUID.randomUUID()` ile sunucu tarafından otomatik oluşturulur (`POST /patients` sırasında).

## 🚀 Çalıştırma

**Gereksinimler:**

- Java 17+
- Maven 3.9+

**Uygulama Başlatma:**

```bash
mvn spring-boot:run
```

**Uygulama URL:**

```
http://localhost:8080
```

## 📁 Veri Dosyası

Veriler şu dosyada saklanır:

```
var/data/patients.txt
```

Her satır bir JSON nesnesidir (NDJSON formatı).

## 🌐 REST API Uç Noktaları

| HTTP   | URL                                           | Açıklama                              |
|--------|-----------------------------------------------|---------------------------------------|
| POST   | `/patients/load?fileName=var/data/patients.txt` | Dosyadan yükle (HashMap’i doldurur)   |
| POST   | `/patients`                                   | Yeni hasta oluşturur                  |
| GET    | `/patients`                                   | Tüm hastaları getirir                 |
| GET    | `/patients/{id}`                              | ID ile hasta getirir                  |
| PUT    | `/patients/{id}`                              | ID ile hastayı günceller              |
| DELETE | `/patients/{id}`                              | ID ile hastayı siler                  |
| POST   | `/patients/save?fileName=var/data/patients.txt` | Verileri dosyaya kaydeder ve temizler |

## 🧪 Hızlı Test Senaryosu (`curl`)

### 1. Dosyadan veri yükle

```bash
curl -X POST "http://localhost:8080/patients/load?fileName=var/data/patients.txt"
```

### 2. Yeni hasta oluştur

```bash
curl -X POST "http://localhost:8080/patients" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ali",
    "surname": "Yılmaz",
    "age": 29,
    "phone": "555-000-11-22",
    "email": "ali@example.com"
  }'
```

### 3. Tüm hastaları getir

```bash
curl -X GET "http://localhost:8080/patients"
```

### 4. ID ile hasta getir

```bash
ID="<<<POST cevabında dönen id>>>"
curl -X GET "http://localhost:8080/patients/$ID"
```

### 5. Hasta güncelle

```bash
curl -X PUT "http://localhost:8080/patients/$ID" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ali",
    "surname": "Yılmaz",
    "age": 30,
    "phone": "555-000-33-44",
    "email": "ali.new@example.com"
  }'
```

### 6. Hasta sil

```bash
curl -X DELETE "http://localhost:8080/patients/$ID"
```

### 7. Verileri kaydet ve belleği temizle

```bash
curl -X POST "http://localhost:8080/patients/save?fileName=var/data/patients.txt"
```

> `save` işlemi sonrası `HashMap` temizlenir. Devam etmek için `load` yap ve yeni hasta ekle.

## 🧾 patients.txt Formatı (NDJSON)

```json
{"id":"c0a1...","name":"Ali","surname":"Yılmaz","age":29,"phone":"555-000-11-22","email":"ali@example.com"}
{"id":"9f3d...","name":"Bob","surname":"Demir","age":41,"phone":"555-111-22-33","email":"bob@example.com"}
```

---
