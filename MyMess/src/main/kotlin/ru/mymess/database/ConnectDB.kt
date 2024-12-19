package ru.mymess.database

import ru.mymess.DataModels.Dialogues
import ru.mymess.DataModels.Friend
import ru.mymess.DataModels.Message
import ru.mymess.DataModels.SendFriend
import java.sql.*

class ConnectDB {

    fun getAllUsers(): List<User> {
        val connection = connect()
        val sql = "SELECT * FROM users"
        val users = mutableListOf<User>()

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val login = resultSet.getString("login")
                val userName = resultSet.getString("userName")
                val password = resultSet.getString("password")
                val image = resultSet.getString("image")
                users.add(User(id, login, userName, password, image))
            }
        } catch (e: Exception) {
            println("Ошибка при получении данных: ${e.message}")
        } finally {
            connection.close()
        }
        return users
    }

    fun isUserExist(login: String): Boolean {

        var exists = false

        val connection = connect()

        try {
            val query = "SELECT * FROM users WHERE login = ? LIMIT 1"
            val statement = connection.prepareStatement(query)

            statement.setString(1, login)


            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                exists = true
                println(exists)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return exists
    }
    fun getUserByLogin(login: String): User? {
        val connection = connect()
        val sql = "SELECT * FROM users WHERE login = ?"
        var user: User? = null
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, login)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val id = resultSet.getInt("id")
                val login = resultSet.getString("login")
                val userName = resultSet.getString("userName")
                val password = resultSet.getString("password")
                val image = resultSet.getString("image")
                user = User(id, login, userName, password, image)
            } else {
                println("User with login $login not found")
            }
        } catch (e: Exception) {
            println("Error receiving user: ${e.message}")
        } finally {
            connection.close()
        }

        return user
    }

    fun getUserById(id: Int): User {
        val connection = connect()
        val sql = "SELECT * FROM users WHERE id = ?"
        var user: User? = null
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val id = resultSet.getInt("id")
                val login = resultSet.getString("login")
                val userName = resultSet.getString("userName")
                val password = resultSet.getString("password")
                val image = resultSet.getString("image")
                user = User(id, login, userName, password, image)
            } else {
                println("User with login $id not found")
            }
        } catch (e: Exception) {
            println("Error receiving user: ${e.message}")
        } finally {
            connection.close()
        }

        return user ?: throw Exception("User with login $id not found")
    }

    fun insertUser(login: String, password: String) {
        val connection = connect()
        val sql = "INSERT INTO users (login, userName, password) VALUES (?, ?, ?)"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, login)
            preparedStatement.setString(2, "Test")
            preparedStatement.setString(3, password)
            preparedStatement.executeUpdate()
            println("Пользователь успешно добавлен в базу данных")
        } catch (e: Exception) {
            println("Ошибка при вставке данных: ${e.message}")
        } finally {
            connection.close()
        }
    }

    fun searchUsersByName(query: String): List<SendFriend> {
        val users = mutableListOf<SendFriend>()
        val sql = "SELECT id, userName, image FROM users WHERE userName LIKE ?"

        val connection = connect()
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, "%$query%")

            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val userName = resultSet.getString("userName")
                val image = resultSet.getString("image")?: ""


                users.add(SendFriend(0, id, userName, image))
            }
        } catch (e: Exception) {
            println("Ошибка при поиске данных: ${e.message}")
        } finally {
            connection.close()
        }

        return users
    }


    // Функция для удаления пользователя по id
    fun deleteUser(id: Int) {
        val connection = connect()
        val sql = "DELETE FROM users WHERE id = ?"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            preparedStatement.executeUpdate()
            println("Пользователь с id $id успешно удален из базы данных")
        } catch (e: Exception) {
            println("Ошибка при удалении данных: ${e.message}")
        } finally {
            connection.close()
        }

    }

    // Функция для обновления информации о пользователе
    fun updateUser(id: Int, newUserName: String, newPassword: String) {
        val connection = connect()
        val sql = "UPDATE users SET userName = ?, password = ? WHERE id = ?"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, newUserName)
            preparedStatement.setString(2, newPassword)
            preparedStatement.setInt(3, id)
            preparedStatement.executeUpdate()
            println("Информация о пользователе с id $id успешно обновлена")
        } catch (e: Exception) {
            println("Ошибка при обновлении данных: ${e.message}")
        } finally {
            connection.close()
        }
    }

    fun getFriends(userId: Int): List<Friend>{
        val connection = connect()
        val sql = "SELECT * FROM friends WHERE idUser = ? OR idFriend = ?"
        val friends = mutableListOf<Friend>()

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, userId)
            preparedStatement.setInt(2, userId)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val idUser = resultSet.getInt("idUser")
                val idFriend = resultSet.getInt("idFriend")
                friends.add(Friend(id, idUser, idFriend))
            }
        } catch (e: Exception) {
            println("Ошибка при получении данных: ${e.message}")
        } finally {
            connection.close()
        }
        return friends
    }
    suspend fun loadUsersIntoList() {
        ListUsers.listUsers = getAllUsers().toMutableList()
    }

    fun updateUserImage(id: Int, newImage: String) {
        val connection = connect()
        val sql = "UPDATE users SET image = ? WHERE id = ?"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, newImage)
            preparedStatement.setInt(2, id)
            preparedStatement.executeUpdate()
            println("Информация о пользователе с id $id успешно обновлена")
        } catch (e: Exception) {
            println("Ошибка при обновлении данных: ${e.message}")
        } finally {
            connection.close()
        }
    }
    

    fun uploadImage(Image: ByteArray) {
        val connection = connect()
        val sql = "INSERT INTO images (Image) VALUES (?)"

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setBytes(1, Image)
            preparedStatement.executeUpdate()
            println("Пользователь успешно добавлен в базу данных")
        } catch (e: Exception) {
            println("Ошибка при вставке данных: ${e.message}")
        } finally {
            connection.close()
        }
    }

    fun getImageById(id: Int): ByteArray? {
        val connection = connect()
        val sql = "SELECT Image FROM images WHERE id = ?"
        var image: ByteArray? = null

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                image = resultSet.getBytes("Image")
            } else {
                println("Image with id $id not found")
            }
        } catch (e: Exception) {
            println("Error while retrieving image: ${e.message}")
        } finally {
            connection.close()
        }

        return image
    }



    fun getMessagesByDialogId(idDialog: Int): List<Message> {
        val connection = connect()  // Здесь connect() возвращает соединение с базой данных
        val sql = "SELECT id, id_dialog, id_sender, message, timeMessage FROM messages WHERE id_dialog = ?"
        val messages = mutableListOf<Message>()

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, idDialog)  // Устанавливаем значение параметра id_sender
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val idDialog = resultSet.getInt("id_dialog")
                val idSender = resultSet.getInt("id_sender")
                val message = resultSet.getString("message")
                val timeMessage = resultSet.getString("timeMessage")
                messages.add(Message(id, idDialog, idSender, message, timeMessage))
            }
        } catch (e: SQLException) {
            println("Ошибка при получении данных: ${e.message}")
        } finally {
            connection.close()
        }
        return messages
    }
    fun insertMessage(idDialog: Int, idSender: Int, message: String, timeMessage: String): Boolean {
        val connection = connect()  // Предполагается, что connect() возвращает соединение с базой данных
        val sql = "INSERT INTO messages (id_dialog, id_sender, message, timeMessage) VALUES (?, ?, ?, ?)"
        var isInserted = false

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

            preparedStatement.setInt(1, idDialog)  // Устанавливаем значение id_dialog
            preparedStatement.setInt(2, idSender)  // Устанавливаем значение id_sender
            preparedStatement.setString(3, message)  // Устанавливаем значение message
            preparedStatement.setString(4, timeMessage)  // Устанавливаем значение timeMessage

            val rowsAffected = preparedStatement.executeUpdate()  // Выполняем вставку данных

            isInserted = rowsAffected > 0  // Если вставлено хотя бы одно сообщение, возвращаем true
        } catch (e: SQLException) {
            println("Ошибка при вставке данных: ${e.message}")
        } finally {
            connection.close()  // Закрываем соединение в блоке finally
        }

        return isInserted
    }

    fun getAllDialogues(userId: Int): List<Dialogues>{
        val connection = connect()
        val sql = "SELECT * FROM dialogues WHERE participant1 = ? OR participant2 = ?"
        val dialogues = mutableListOf<Dialogues>()

        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, userId)  // Устанавливаем значение параметра id_sender
            preparedStatement.setInt(2, userId)  // Устанавливаем значение параметра id_sender
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val participant1 = resultSet.getInt("participant1")
                val participant2 = resultSet.getInt("participant2")
                dialogues.add(Dialogues(id, participant1, participant2))
            }
        } catch (e: Exception) {
            println("Ошибка при получении данных: ${e.message}")
        } finally {
            connection.close()
        }
        return dialogues
    }
    private fun connect(): Connection {
        val url = "jdbc:sqlite:src/main/kotlin/ru/mymess/database/databaseForMyMess.db" //Вставьте путь к ДБ
        return DriverManager.getConnection(url)
    }
}

