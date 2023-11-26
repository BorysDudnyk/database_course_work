# ЗАГОЛОВОК
puts "Запускаємо тест UserTest"

# БЛОК
puts "Створюємо та перевіряємо об'єкт класу User"

# Створення екземпляра об'єкту User
set user [list "John" "Doe" "johndoe" "password" "Location" "Male"]
set newUser [dict create]
dict set newUser "firstName" [lindex $user 0]
dict set newUser "lastName" [lindex $user 1]
dict set newUser "userName" [lindex $user 2]
dict set newUser "password" [lindex $user 3]
dict set newUser "location" [lindex $user 4]
dict set newUser "gender" [lindex $user 5]

# Перевірка значень полів об'єкта
if {[dict get $newUser "firstName"] ne "John"} {
    puts "Error: First name doesn't match."
    puts "Failed"
    exit 1
}

if {[dict get $newUser "lastName"] ne "Doe"} {
    puts "Error: Last name doesn't match."
    puts "Failed"
    exit 1
}

if {[dict get $newUser "userName"] ne "johndoe"} {
    puts "Error: User name doesn't match."
    puts "Failed"
    exit 1
}

if {[dict get $newUser "password"] ne "password"} {
    puts "Error: Password doesn't match."
    puts "Failed"
    exit 1
}

if {[dict get $newUser "location"] ne "Location"} {
    puts "Error: Location doesn't match."
    puts "Failed"
    exit 1
}

if {[dict get $newUser "gender"] ne "Male"} {
    puts "Error: Gender doesn't match."
    puts "Failed"
    exit 1
}

puts "Всі перевірки пройдені успішно."
puts "Тест пройдений успішно."
