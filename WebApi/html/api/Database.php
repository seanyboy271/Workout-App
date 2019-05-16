<?php
/**
 * Created by PhpStorm.
 * User: ianburns
 * Date: 2019-03-12
 * Time: 15:58
 */

include(__DIR__ . '/../../inc/dbinfo.php');

class Database
{
    private static $_instance = null;

    protected function __construct()
    {
        $opt = array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION);
        $dsn = "mysql:host=" . DB_SERVER . ";port=" . DB_PORT . ";charset=utf8";

        try {
            $this->_pdo = new PDO($dsn, DB_USERNAME, DB_PASSWORD, $opt);
        } catch (PDOException $e) {
            echo $e;
        }
    }

    public static function getInstance()
    {
        if (!isset(self::$_instance)) {
            self::$_instance = new Database();
        }
        return self::$_instance;
    }

    public function query($sql, $params = array())
    {
        $stmt = $this->_pdo->prepare($sql);
        $stmt->execute($params);
        return $stmt;
    }

    public function execute($sql, $params = array())
    {
        $stmt = $this->_pdo->prepare($sql);
        $stmt->execute($params);
        return $stmt->rowCount();
    }
}