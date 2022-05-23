# Study about clojure language

## Technologies used:

```clojure
[org.clojure/clojure "1.11.1"]
[org.clojure/data.json "2.4.0"]
[prismatic/schema "1.2.1"]
```

## Environment requirements

* JDK >= 11
* Clojure
* Lein

## Executando o programa

```bash
lein run
```
- You should receive the following output
```json
[{"tax":0},{"tax":0},{"tax":20000.0},{"tax":30000.0}]
```
## Description files

| File             | Description              |
|------------------|--------------------------| 
| `core`           | Main file                |
| `database`       | Mock of the data         |
| `logic`          | Business logic           |
| `model`          | Model of business entity |
| `serializer`     | Serializer of data       |
| `core_test`      | Unit tests (wip)         |

## Testes

Execute tests:
```
wip
```